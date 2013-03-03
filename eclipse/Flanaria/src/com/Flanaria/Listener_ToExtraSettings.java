package com.Flanaria;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;

public class Listener_ToExtraSettings implements OnPreferenceClickListener
{
	private Context _context;
	private List<Long> _list;

	public Listener_ToExtraSettings(Context context)
	{
		_context = context;
		_list = new ArrayList<Long>();
		_list.clear();
	}

	public boolean onPreferenceClick(Preference preference)
	{
		_list.add(Long.valueOf(System.currentTimeMillis()));
		if(_list.size() >= 10)
		{
			doIntent();
			_list.clear();
		}
		return true;
	}

	private void doIntent()
	{
		IntentUtils.intentWrap((Activity)_context, "Activity_ExtraPreference");
	}
}
