package com.Flanaria;

import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;

public class Listener_MuteTypeChanged implements OnPreferenceChangeListener
{
	public boolean onPreferenceChange(Preference preference, Object newValue)
	{
		Logger.d("Setting To "+newValue.toString());
		String s = (String)newValue;
		CurrentTasks.getInstance().setMuteTypeSetting(Integer.valueOf(s));
		return true;
	}
}
