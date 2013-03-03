package com.Flanaria;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

public class AsyncTask_Intent_Authorize extends AsyncTask<String, Integer, Long>
{
	private AlertDialog.Builder _builder;
	private AlertDialog _dialog;
	private Activity _activity;

	public AsyncTask_Intent_Authorize(Activity activity)
	{
		_activity = activity;
	}

	@Override
	protected void onPostExecute(Long result)
	{
		_dialog = _builder.create();
		_dialog.show();
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		_builder = DialogUtils.createDialogBuilder(_activity,
				_activity.getResources().getString(R.string.Authorize_Pre_Title),
				_activity.getResources().getString(R.string.Authorize_Pre_Message));
		_builder.setPositiveButton(R.string.Authorize_Pre_Positive, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				IntentUtils.intent(_activity, "Activity_Authorize");
			}
		});
		return Long.valueOf(0);
	}
}
