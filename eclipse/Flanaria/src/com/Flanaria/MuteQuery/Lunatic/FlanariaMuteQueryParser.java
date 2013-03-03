package com.Flanaria.MuteQuery.Lunatic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.Flanaria.Logger;

import twitter4j.DirectMessage;
import twitter4j.Status;

public class FlanariaMuteQueryParser
{
	public static boolean isShouldFilter(Status status, String queryString)
	{
		queryString = queryString.replace(" ", "");
		queryString = "("+queryString+")";
		String squery;
		while(true)
		{
			squery = getShortestBracket(queryString);
			if(squery == null)
			{
				break;
			}
			String buffer = squery;
			squery = parse(squery, status);
			Logger.d("FlanQ.Replace:("+buffer+", "+squery+")");
			queryString = queryString.replace(buffer, squery);
		}
		return parseLogics(queryString);
	}

	public static boolean isShouldFilter(DirectMessage msg, String queryString)
	{
		queryString = queryString.replace(" ", "");
		queryString = "("+queryString+")";
		String squery;
		while(true)
		{
			squery = getShortestBracket(queryString);
			if(squery == null)
			{
				break;
			}
			String buffer = squery;
			squery = parse(squery, msg);
			Logger.d("FlanQ.Replace:("+buffer+", "+squery+")");
			queryString = queryString.replace(buffer, squery);
		}
		return parseLogics(queryString);
	}

	private static boolean parseLogics(String queryString)
	{
		String buffer = queryString;
		queryString = queryString.replace("!true", "false");
		queryString = queryString.replace("!false", "true");
		queryString = queryString.replace("false&&false", "false");
		queryString = queryString.replace("true&&false", "false");
		queryString = queryString.replace("false&&true", "false");
		queryString = queryString.replace("true&&true", "true");
		queryString = queryString.replace("false||false", "false");
		queryString = queryString.replace("true||false", "true");
		queryString = queryString.replace("false||true", "true");
		queryString = queryString.replace("true||true", "true");
		if(queryString.equals("true") || queryString.equals("false"))
		{
			return Boolean.valueOf(queryString);
		}
		if(buffer.equals(queryString))
		{
			return false;
		}
		return parseLogics(queryString);
	}

	private static String parse(String squery, Status status)
	{
		for(int i = 0; i < Retentioner_Query.getInstance().getStringQueryCount(); i++)
		{
			if(squery.contains("{"+i+"}"))
			{
				squery = squery.replace("{"+i+"}", Boolean.valueOf(Retentioner_Query.getInstance().getStringQuery(i).getIsMatch(status)).toString());
			}
		}
		for(int i = 0; i < Retentioner_Query.getInstance().getLongQueryCount(); i++)
		{
			if(squery.contains("["+i+"]"))
			{
				squery = squery.replace("["+i+"]", Boolean.valueOf(Retentioner_Query.getInstance().getLongQuery(i).getIsMatch(status)).toString());
			}
		}
		if(squery.contains("RT"))
		{
			squery = squery.replace("RT", Boolean.valueOf(status.isRetweet()).toString());
		}
		squery = squery.replace("(", "");
		squery = squery.replace(")", "");
		return squery;
	}

	private static String parse(String squery, DirectMessage msg)
	{
		for(int i = 0; i < Retentioner_Query.getInstance().getStringQueryCount(); i++)
		{
			if(squery.contains("{"+i+"}"))
			{
				squery = squery.replace("{"+i+"}", Boolean.valueOf(Retentioner_Query.getInstance().getStringQuery(i).getIsMatch(msg)).toString());
			}
		}
		for(int i = 0; i < Retentioner_Query.getInstance().getLongQueryCount(); i++)
		{
			if(squery.contains("["+i+"]"))
			{
				squery = squery.replace("["+i+"]", Boolean.valueOf(Retentioner_Query.getInstance().getLongQuery(i).getIsMatch(msg)).toString());
			}
		}
		squery = squery.replace("(", "");
		squery = squery.replace(")", "");
		return squery;
	}

	private static String getShortestBracket(String text)
	{
		Pattern pattern = Pattern.compile("\\((?!\\().*?\\)");
		Matcher matcher = pattern.matcher(text);
		if(matcher.find())
		{
			String s = matcher.group();
			Logger.d("FlanQ.Find:"+s);
			return s;
		}
		return null;
	}
}
