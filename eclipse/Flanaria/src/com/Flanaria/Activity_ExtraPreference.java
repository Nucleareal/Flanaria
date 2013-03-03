package com.Flanaria;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Activity_ExtraPreference extends PreferenceActivity
{
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pref_extend);
        addPreferencesFromResource(R.xml.xpref);
    }
}
