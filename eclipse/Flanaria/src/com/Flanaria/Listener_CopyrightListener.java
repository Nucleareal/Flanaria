package com.Flanaria;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.view.LayoutInflater;

public class Listener_CopyrightListener implements OnPreferenceClickListener
{
	private Activity _parent;

	public Listener_CopyrightListener(Activity parent)
	{
		_parent = parent;
	}

	public boolean onPreferenceClick(Preference preference)
	{
		AlertDialog.Builder builder = new Builder(_parent);
		LayoutInflater inflater = _parent.getLayoutInflater();
		builder.setCustomTitle(inflater.inflate(R.layout.lisence_dialog_title, null));
		builder.setView(inflater.inflate(R.layout.lisence_dialog, null));
		builder.setPositiveButton(R.string.OK, null);
		builder.create().show();

		return true;
	}
}
