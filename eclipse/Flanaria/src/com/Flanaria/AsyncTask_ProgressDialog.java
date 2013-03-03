package com.Flanaria;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class AsyncTask_ProgressDialog extends AsyncTask<String, Integer, Long>
{
	private Context _context;
	private ProgressDialog _dialog;
	protected boolean _isFinished;
	private String _title;
	private String _message;

	public AsyncTask_ProgressDialog(Context context, String title, String message)
	{
		_context = context;
		_title = title;
		_message = message;
	}

	@Override
	protected void onPreExecute()
	{
		_isFinished = false;
		_dialog = new ProgressDialog(_context);
		_dialog.setTitle(_title);
		_dialog.setMessage(_message);
		_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		_dialog.setCancelable(false);
		_dialog.show();
	}

	@Override
	protected void onPostExecute(Long result)
	{
		_dialog.dismiss();
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		while(!_isFinished)
		{
			try
			{
				Thread.sleep(1);
			}
			catch(InterruptedException e)
			{
				break;
			}
		}
		return Long.valueOf(0);
	}

	//CurrentTasks.getInstance().getCurrentAuthorize().getState() != Enum_AuthorizeState.Completed

	/*public boolean getIsFinished()
	{
		return _isFinished;
	}*/

	public void stop()
	{
		_isFinished = true;
	}
}
