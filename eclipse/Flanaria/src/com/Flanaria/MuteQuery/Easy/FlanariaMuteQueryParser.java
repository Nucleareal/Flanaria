package com.Flanaria.MuteQuery.Easy;

import java.util.List;

import android.text.Html;

import twitter4j.DirectMessage;
import twitter4j.Status;

public class FlanariaMuteQueryParser
{
	public static boolean isShouldFilter(Status status)
	{
		List<String> Names = Retentioner_Query.getInstance().getScreenNameFilters();
		List<String> Apps  = Retentioner_Query.getInstance().getClientFilters();
		List<String> Words = Retentioner_Query.getInstance().getWordFilters();

		String scrn = status.getUser().getScreenName();
		String scrn_r = null;
		String text = status.getText();
		String appname = Html.fromHtml(status.getSource()).toString();
		if(status.isRetweet())
		{
			scrn_r = status.getRetweetedStatus().getUser().getScreenName();
		}

		for(String s : Names)
		{
			if(scrn.equals(s))
			{
				return true;
			}
			if(scrn_r != null && scrn_r.equals(s))
			{
				return true;
			}
			if(text.contains(s))
			{
				return true;
			}
		}
		for(String s : Apps)
		{
			if(appname.equals(s))
			{
				return true;
			}
		}
		for(String s : Words)
		{
			if(text.contains(s))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isShouldFilter(DirectMessage msg)
	{
		List<String> Names = Retentioner_Query.getInstance().getScreenNameFilters();
		List<String> Words = Retentioner_Query.getInstance().getWordFilters();

		String scrn = msg.getSender().getScreenName();
		String scrn_r = null;
		String text = msg.getText();

		for(String s : Names)
		{
			if(scrn.equals(s))
			{
				return true;
			}
			if(scrn_r != null && scrn_r.equals(s))
			{
				return true;
			}
			if(text.contains(s))
			{
				return true;
			}
		}
		for(String s : Words)
		{
			if(text.contains(s))
			{
				return true;
			}
		}
		return false;
	}
}
