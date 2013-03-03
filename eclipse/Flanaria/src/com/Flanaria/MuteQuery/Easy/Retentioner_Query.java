package com.Flanaria.MuteQuery.Easy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

import com.Flanaria.R;
import com.Flanaria.Wrapper_SharedPreference;

import android.content.Context;

public class Retentioner_Query
{
	private Retentioner_Query()
	{
	}
	private static Retentioner_Query _ins;
	static
	{
		_ins = new Retentioner_Query();
	}
	public static Retentioner_Query getInstance()
	{
		return _ins;
	}

	private String _query;
	private LinkedList<String> _ScreenNames;
	private LinkedList<String> _Clients;
	private LinkedList<String> _Words;

	public void refresh()
	{
		_ScreenNames = new LinkedList<String>();
		_Clients = new LinkedList<String>();
		_Words = new LinkedList<String>();
	}

	public void parseQuery(String query)
	{
		_query = query;
		parseQuery();
	}

	private void parseQuery()
	{
		_query.replace(" ", "");
		StringTokenizer sz = new StringTokenizer(_query, ",");
		ArrayList<String> list = new ArrayList<String>(); list.clear();
		while(sz.hasMoreTokens())
		{
			list.add(sz.nextToken());
		}
		for(String s : list)
		{
			if(s.startsWith("@"))
			{
				_ScreenNames.add(s.replace("@", ""));
			} else
			if(s.startsWith("$"))
			{
				_Clients.add(s.replace("$", ""));
			} else
			{
				_Words.add(s);
			}
		}
	}

	public LinkedList<String> getScreenNameFilters()
	{
		return _ScreenNames;
	}

	public LinkedList<String> getClientFilters()
	{
		return _Clients;
	}

	public LinkedList<String> getWordFilters()
	{
		return _Words;
	}

	public void load(Context context)
	{
		String key = context.getString(R.string.SP_Query_Easy);
		parseQuery(Wrapper_SharedPreference.getInstance().getString(context, key));
	}

	public void save(Context context)
	{
		String key = context.getString(R.string.SP_Query_Easy);
		Wrapper_SharedPreference.getInstance().putString(context, key, _query);
	}

	public void resetQuery(Context context, String query)
	{
		refresh();
		parseQuery(query);
		save(context);
	}

	public CharSequence getRawQuery()
	{
		return _query;
	}
}
