package com.Flanaria;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;

public class Activity_Preference extends PreferenceActivity
{
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
        initialize();
    }

	private void initialize()
	{
		getListView().setScrollingCacheEnabled(false);
		
		CheckBoxPreference pref0 = (CheckBoxPreference)findPreference("MuteSettings_Category_EnableMute");
		pref0.setOnPreferenceChangeListener(new Listener_MuteEnableChanged());
		ListPreference pref1 = (ListPreference)findPreference("MuteSettings_Category_SelectType");
		pref1.setOnPreferenceChangeListener(new Listener_MuteTypeChanged());

		Preference pref2 = (Preference)findPreference("MuteSettings_Category_SettingFilter");
		pref2.setOnPreferenceClickListener(new Listener_MuteSettingFilter(this));

		PreferenceCategory fallback = (PreferenceCategory)findPreference("FallSettings_To");
		AsyncTask_AddFallbackToSettings task0 = new AsyncTask_AddFallbackToSettings(this, fallback);
		task0.execute(getText(R.string.app_name).toString());

		PreferenceCategory active = (PreferenceCategory)findPreference("ActiveSettings_Category");
		AsyncTask_AddActiveSettings task1 = new AsyncTask_AddActiveSettings(this, active);
		task1.execute(getText(R.string.app_name).toString());

		Preference pref4 = (Preference)findPreference(getString(R.string.Pref_Copyright));
		pref4.setOnPreferenceClickListener(new Listener_CopyrightListener(this));

		Preference pref3 = (Preference)findPreference(getString(R.string.Pref_ExtraSettings));
		pref3.setOnPreferenceClickListener(new Listener_ToExtraSettings(this));
	}
}
