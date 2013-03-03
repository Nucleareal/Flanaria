package com.Flanaria;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceCategory;

public class AsyncTask_AddActiveSettings extends AsyncTask<String, Integer, Long>
{
	private Context _context;
	private PreferenceCategory _category;

	public AsyncTask_AddActiveSettings(Context context, PreferenceCategory category)
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
		List<ObjectPair<String, Boolean>> list = Retentioner_Users.getInstance().getAllUserActiveSets();
		for(ObjectPair<String, Boolean> obj : list)
		{
			CheckBoxPreference pref = new CheckBoxPreference(_context);
			pref.setTitle(obj.getValue1());
			pref.setSummary(_context.getText(R.string.ActiveAccount_Summary));
			pref.setDefaultValue(Boolean.valueOf(obj.getValue2().booleanValue()));
			pref.setOnPreferenceChangeListener(new Listener_AccountActiveChanged(obj.getValue1()));
			_category.addPreference(pref);
		}
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		return Long.valueOf(0L);
	}
}
