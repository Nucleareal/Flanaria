package com.Flanaria;

import android.content.Context;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;

public class SPListener_Bool implements OnPreferenceChangeListener
{
	private Context _context;
	private String _key;

	public SPListener_Bool(Context context, String key)
	{
		_context = context;
		_key = key;
	}

	public boolean onPreferenceChange(Preference pref, Object obj)
	{
		Boolean value = (Boolean)obj;
		Wrapper_SharedPreference.getInstance().putBoolean(_context, _key, value.booleanValue());
		return true;
	}
}
