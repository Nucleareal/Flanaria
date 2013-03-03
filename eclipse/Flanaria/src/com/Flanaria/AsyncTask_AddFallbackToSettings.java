package com.Flanaria;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.ListPreference;
import android.preference.PreferenceCategory;

public class AsyncTask_AddFallbackToSettings extends AsyncTask<String, Integer, Long>
{
	private Context _context;
	private PreferenceCategory _category;

	public AsyncTask_AddFallbackToSettings(Context context, PreferenceCategory category)
	{
		_context = context;
		_category = category;
	}

	@Override
	protected void onPreExecute()
	{
	}

	@Override
	protected void onPostExecute(Long result)
	{
		List<ObjectPair<String, VerifiedUser>> list = Retentioner_Users.getInstance().getAllUsetFallbackToSets();
		String[] entryValues = getEntryValues(list);
		for(ObjectPair<String, VerifiedUser> obj : list)
		{
			String[] entries = getEntry(list, obj.getValue1());
			ListPreference pref = new ListPreference(_context);
			pref.setTitle(obj.getValue1());
			pref.setSummary(ReplaceUtils.replaceUserNames(_context.getString(R.string.Account_FallBack_Summary), Retentioner_Users.getInstance().forName(obj.getValue1())));
			pref.setEntries(entries);
			pref.setEntryValues(entryValues);
			pref.setDefaultValue(getDefaultValue(obj.getValue1()));
			pref.setOnPreferenceChangeListener(new Listener_FallbackSetting(obj.getValue1()));
			_category.addPreference(pref);
		}
	}

	private String getDefaultValue(String value1)
	{
		VerifiedUser user = Retentioner_Users.getInstance().forName(value1);
		if(user == null || user.getFallBackToUser() == null)
		{
			return user.getScreenName();
		}
		return user.getFallBackToUser().getScreenName();
	}

	private String[] getEntry(List<ObjectPair<String, VerifiedUser>> list, String value1)
	{
		String[] res = getEntryValues(list);
		for(int i = 0; i < res.length; i++)
		{
			if(res[i].equals(value1))
			{
				res[i] = _context.getString(R.string.Account_Set_No_Fallback);
				break;
			}
		}
		return res;
	}

	private String[] getEntryValues(List<ObjectPair<String, VerifiedUser>> list)
	{
		String[] res = new String[list.size()];
		for(int i = 0; i < res.length; i++)
		{
			ObjectPair<String, VerifiedUser> obj = list.get(i);
			res[i] = obj.getValue1();
		}
		return res;
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		return Long.valueOf(0L);
	}
}
