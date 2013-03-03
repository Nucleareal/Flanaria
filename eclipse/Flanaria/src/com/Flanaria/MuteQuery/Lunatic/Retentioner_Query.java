package com.Flanaria.MuteQuery.Lunatic;

import java.util.ArrayList;
import java.util.List;

import com.Flanaria.IFlanariaConsumer;
import com.Flanaria.IReplaceStrings;
import com.Flanaria.R;
import com.Flanaria.Wrapper_SharedPreference;

import android.content.Context;

public class Retentioner_Query implements IReplaceStrings, IFlanariaConsumer
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

	private List<IFlanariaMuteQuery<String>> _StringList;
	private List<IFlanariaMuteQuery<Long>> _LongList;
	private String _queryString;

	public void refresh()
	{
		_StringList = new ArrayList<IFlanariaMuteQuery<String>>();
		_LongList = new ArrayList<IFlanariaMuteQuery<Long>>();
	}

	public void addStringQuery(IFlanariaMuteQuery<String> query)
	{
		query.setNumber(_StringList.size());
		if(!_StringList.contains(query))
		{
			_StringList.add(query);
		}
	}
	public void deleteStringQuery(int index)
	{
		if(index < _StringList.size() && index >= 0)
		{
			_StringList.remove(index);
		}
	}
	public IFlanariaMuteQuery<String> getStringQuery(int index)
	{
		if(index < _StringList.size() && index >= 0)
		{
			return _StringList.get(index);
		}
		return null;
	}
	public int getStringQueryCount()
	{
		return _StringList.size();
	}

	public void addLongQuery(IFlanariaMuteQuery<Long> query)
	{
		query.setNumber(_LongList.size());
		if(!_LongList.contains(query))
		{
			_LongList.add(query);
		}
	}
	public void deleteLongQuery(int index)
	{
		if(index < _LongList.size() && index >= 0)
		{
			_LongList.remove(index);
		}
	}
	public IFlanariaMuteQuery<Long> getLongQuery(int index)
	{
		if(index < _LongList.size() && index >= 0)
		{
			return _LongList.get(index);
		}
		return null;
	}
	public int getLongQueryCount()
	{
		return _LongList.size();
	}

	public void renewQuery(String query, Context context)
	{
		_queryString = query;
		saveQueryString(context);
	}

	public void load(Context context)
	{
		String ct = context.getResources().getString(R.string.SP_Query_CompareType_String);
		String mt = context.getResources().getString(R.string.SP_Query_MatchType_String);
		String ms = context.getResources().getString(R.string.SP_Query_MuteSource_String);
		String tx = context.getResources().getString(R.string.SP_Query_CompareText_String);
		String en = context.getResources().getString(R.string.SP_Query_EnableRetweet_String);
		for(int i = 0; ; i++)
		{
			int cti = Wrapper_SharedPreference.getInstance().getInteger(context, ct.replace(NUMBER, String.valueOf(i)), -1);
			int mti = Wrapper_SharedPreference.getInstance().getInteger(context, mt.replace(NUMBER, String.valueOf(i)), -1);
			int msi = Wrapper_SharedPreference.getInstance().getInteger(context, ms.replace(NUMBER, String.valueOf(i)), -1);
			String txs = Wrapper_SharedPreference.getInstance().getString(context, tx.replace(NUMBER, String.valueOf(i)), _END_OF_DATA_);
			boolean enb = Wrapper_SharedPreference.getInstance().getBoolean(context, en.replace(NUMBER, String.valueOf(i)), false);
			if(cti < 0)
			{
				break;
			}
			addStringQuery(new FlanariaMuteQuery_String(
					Enum_MuteSource_String.value(msi),
					Enum_MuteCompareType_String.value(cti),
					txs,
					Enum_MuteMatchType_String.value(mti),
					enb));
		}
		ct = context.getResources().getString(R.string.SP_Query_CompareType_Long);
		mt = context.getResources().getString(R.string.SP_Query_MatchType_Long);
		ms = context.getResources().getString(R.string.SP_Query_MuteSource_Long);
		tx = context.getResources().getString(R.string.SP_Query_CompareText_Long);
		en = context.getResources().getString(R.string.SP_Query_EnableRetweet_Long);
		for(int i = 0; ; i++)
		{
			int cti = Wrapper_SharedPreference.getInstance().getInteger(context, ct.replace(NUMBER, String.valueOf(i)), -1);
			int mti = Wrapper_SharedPreference.getInstance().getInteger(context, mt.replace(NUMBER, String.valueOf(i)), -1);
			int msi = Wrapper_SharedPreference.getInstance().getInteger(context, ms.replace(NUMBER, String.valueOf(i)), -1);
			long txs = Wrapper_SharedPreference.getInstance().getLong(context, tx.replace(NUMBER, String.valueOf(i)), 0L);
			boolean enb = Wrapper_SharedPreference.getInstance().getBoolean(context, en.replace(NUMBER, String.valueOf(i)), false);
			if(cti < 0)
			{
				break;
			}
			addLongQuery(new FlanariaMuteQuery_Long(
					Enum_MuteSource_Long.value(msi),
					Enum_MuteCompareType_Long.value(cti),
					txs,
					Enum_MuteMatchType_Long.value(mti),
					enb));
		}
		_queryString = Wrapper_SharedPreference.getInstance().getString(context, context.getString(R.string.SP_Query_Lunatic));
	}

	public void save(Context context)
	{
		String ct = context.getResources().getString(R.string.SP_Query_CompareType_String);
		String mt = context.getResources().getString(R.string.SP_Query_MatchType_String);
		String ms = context.getResources().getString(R.string.SP_Query_MuteSource_String);
		String tx = context.getResources().getString(R.string.SP_Query_CompareText_String);
		String en = context.getResources().getString(R.string.SP_Query_EnableRetweet_String);
		int i = 0;
		for(i = 0; i < _StringList.size(); i++)
		{
			saveStringQuery(context,
					ct.replace(NUMBER, String.valueOf(i)),
					mt.replace(NUMBER, String.valueOf(i)),
					ms.replace(NUMBER, String.valueOf(i)),
					tx.replace(NUMBER, String.valueOf(i)),
					en.replace(NUMBER, String.valueOf(i)),
					_StringList.get(i));
		}
		saveStringQuery(context,
				ct.replace(NUMBER, String.valueOf(i)),
				mt.replace(NUMBER, String.valueOf(i)),
				ms.replace(NUMBER, String.valueOf(i)),
				tx.replace(NUMBER, String.valueOf(i)),
				en.replace(NUMBER, String.valueOf(i)),
				null);
		ct = context.getResources().getString(R.string.SP_Query_CompareType_Long);
		mt = context.getResources().getString(R.string.SP_Query_MatchType_Long);
		ms = context.getResources().getString(R.string.SP_Query_MuteSource_Long);
		tx = context.getResources().getString(R.string.SP_Query_CompareText_Long);
		en = context.getResources().getString(R.string.SP_Query_EnableRetweet_Long);
		for(i = 0; i < _LongList.size(); i++)
		{
			saveLongQuery(context,
					ct.replace(NUMBER, String.valueOf(i)),
					mt.replace(NUMBER, String.valueOf(i)),
					ms.replace(NUMBER, String.valueOf(i)),
					tx.replace(NUMBER, String.valueOf(i)),
					en.replace(NUMBER, String.valueOf(i)),
					_LongList.get(i));
		}
		saveLongQuery(context,
				ct.replace(NUMBER, String.valueOf(i)),
				mt.replace(NUMBER, String.valueOf(i)),
				ms.replace(NUMBER, String.valueOf(i)),
				tx.replace(NUMBER, String.valueOf(i)),
				en.replace(NUMBER, String.valueOf(i)),
				null);
		saveQueryString(context);
	}

	private void saveQueryString(Context context)
	{
		Wrapper_SharedPreference.getInstance().putString(context, context.getString(R.string.SP_Query_Lunatic), _queryString);
	}

	private void saveStringQuery(Context context, String ct, String mt, String ms, String tx, String en,
			IFlanariaMuteQuery<String> Query)
	{
		if(Query == null)
		{
			Wrapper_SharedPreference.getInstance().putInteger(context, ct, -1);
			return;
		}
		Object[] aobj = Query.getSaveArray();
		Wrapper_SharedPreference.getInstance().putInteger(context, ct, ((Integer)aobj[0]).intValue());
		Wrapper_SharedPreference.getInstance().putInteger(context, mt, ((Integer)aobj[1]).intValue());
		Wrapper_SharedPreference.getInstance().putInteger(context, ms, ((Integer)aobj[2]).intValue());
		Wrapper_SharedPreference.getInstance().putString(context, tx, (String)aobj[3]);
		Wrapper_SharedPreference.getInstance().putBoolean(context, en, ((Boolean)aobj[4]).booleanValue());
	}

	private void saveLongQuery(Context context, String ct, String mt, String ms, String tx, String en,
			IFlanariaMuteQuery<Long> Query)
	{
		if(Query == null)
		{
			Wrapper_SharedPreference.getInstance().putInteger(context, ct, -1);
			return;
		}
		Object[] aobj = Query.getSaveArray();
		Wrapper_SharedPreference.getInstance().putInteger(context, ct, ((Integer)aobj[0]).intValue());
		Wrapper_SharedPreference.getInstance().putInteger(context, mt, ((Integer)aobj[1]).intValue());
		Wrapper_SharedPreference.getInstance().putInteger(context, ms, ((Integer)aobj[2]).intValue());
		Wrapper_SharedPreference.getInstance().putLong(context, tx, ((Long)aobj[3]).longValue());
		Wrapper_SharedPreference.getInstance().putBoolean(context, en, ((Boolean)aobj[4]).booleanValue());
	}

	public List<IFlanariaMuteQuery<?>> getAllQueriesList()
	{
		ArrayList<IFlanariaMuteQuery<?>> list = new ArrayList<IFlanariaMuteQuery<?>>();
		for(IFlanariaMuteQuery<String> query : _StringList)
		{
			list.add(query);
		}
		for(IFlanariaMuteQuery<Long> query : _LongList)
		{
			list.add(query);
		}
		return list;
	}

	public String getRawQuery()
	{
		return _queryString;
	}

	public void removeQuery(IFlanariaMuteQuery<?> query)
	{
		switch(query.getQueryType())
		{
			default :
			case String :
			{
				_StringList.remove(query);
				break;
			}
			case Long :
			{
				_LongList.remove(query);
				break;
			}
		}
	}

	public void addQuery(IFlanariaMuteQuery<?> query)
	{
		switch(query.getQueryType())
		{
			default :
			case String :
			{
				addStringQuery((IFlanariaMuteQuery<String>)query);
				break;
			}
			case Long :
			{
				addLongQuery((IFlanariaMuteQuery<Long>)query);
				break;
			}
		}
	}
}
