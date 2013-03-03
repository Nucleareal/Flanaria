package com.Flanaria;

import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;

public class Listener_FallbackSetting implements OnPreferenceChangeListener
{
	private String _from;

	public Listener_FallbackSetting(String from)
	{
		_from = from;
	}

	public boolean onPreferenceChange(Preference pref, Object obj)
	{
		String to = (String)obj;
		Logger.d("Flan.FallSet:@"+_from+" Set To @:"+to);
		if(!_from.equals(to))
		{
			Retentioner_Users.getInstance().forName(_from).setFallBackToUser(Retentioner_Users.getInstance().forName(to));
		}
		return true;
	}
}
