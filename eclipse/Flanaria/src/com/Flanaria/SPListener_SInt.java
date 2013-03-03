package com.Flanaria;

import android.content.Context;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;

public class SPListener_SInt implements OnPreferenceChangeListener
{
	private Context _context;
	private String _key;

	public SPListener_SInt(Context context, String key)
	{
		_context = context;
		_key = key;
	}

	public boolean onPreferenceChange(Preference pref, Object obj)
	{
		String value = (String)obj;
		try
		{
			Wrapper_SharedPreference.getInstance().putInteger(_context, _key, Integer.valueOf(value));
		}
		catch(Exception e)
		{
		}
		return true;
	}
}
