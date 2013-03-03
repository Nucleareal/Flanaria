package com.Flanaria;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Listener_MuteSettingFilter implements OnPreferenceClickListener
{
	private Activity _activity;
	private EditText _editText;

	public Listener_MuteSettingFilter(Activity activity)
	{
		_activity = activity;
	}

	public boolean onPreferenceClick(Preference preference)
	{
		_activity.runOnUiThread(new Runnable()
		{
			public void run()
			{
				switch(CurrentTasks.getInstance().getMuteType())
				{
					case 0 :
					{
						showFilterDialog();
						break;
					}
					case 1 :
					{
						intentFilterActivity();
						break;
					}
				}
			}
		});
		return true;
	}

	private void showFilterDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(_activity);
		builder.setTitle(_activity.getString(R.string.Dialog_Filter_Title));

		LinearLayout layout = (LinearLayout)_activity.getLayoutInflater().inflate(R.layout.dialog_filter, null);
		builder.setView(layout);
		_editText = (EditText)layout.findViewById(R.id.EditText_Dialog_Filter);
		_editText.setText(com.Flanaria.MuteQuery.Easy.Retentioner_Query.getInstance().getRawQuery());

		builder.setPositiveButton(R.string.Dialog_Filter_Positive, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				String s = _editText.getText().toString();
				com.Flanaria.MuteQuery.Easy.Retentioner_Query.getInstance().resetQuery(_activity, s);
			}
		});
		builder.setNegativeButton(R.string.Dialog_Filter_Negative, null);

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void intentFilterActivity()
	{
		IntentUtils.intent(_activity, "Activity_MuteFilter");
	}
}
