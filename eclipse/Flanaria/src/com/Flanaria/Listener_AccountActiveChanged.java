package com.Flanaria;

import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;

public class Listener_AccountActiveChanged implements OnPreferenceChangeListener
{
	String _name;

	public Listener_AccountActiveChanged(String name)
	{
		_name = name;
	}

	public boolean onPreferenceChange(Preference pref, Object value)
	{
		boolean b = ((Boolean)value).booleanValue();
		Retentioner_Users.getInstance().setUserIsActive(_name, b);
		return true;
	}
}
