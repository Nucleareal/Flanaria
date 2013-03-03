package com.Flanaria;

import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;

public class Listener_MuteEnableChanged implements OnPreferenceChangeListener
{
	public boolean onPreferenceChange(Preference arg0, Object arg1)
	{
		CurrentTasks.getInstance().setMuteEnableSetting(((Boolean)arg1).booleanValue());
		return true;
	}
}
