package com.Flanaria;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class AsyncTask_LoadUsers extends AsyncTask<String, Integer, Long>
{
	private Context _context;
	private ProgressDialog _dialog;
	private boolean _isFinished;
	private IAsyncTaskParent _parent;

	public AsyncTask_LoadUsers(Context context, IAsyncTaskParent parent)
	{
		_context = context;
		_parent = parent;
	}

	@Override
	protected void onPreExecute()
	{
		_isFinished = false;
		_dialog = new ProgressDialog(_context);
		_dialog.setTitle(R.string.Timeline_LoadUser_Title);
		_dialog.setMessage(_context.getResources().getText(R.string.Timeline_LoadUser_Message));
		_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		_dialog.setCancelable(false);
		_dialog.show();
	}

	@Override
	protected void onPostExecute(Long result)
	{
		_dialog.dismiss();
		_parent.onTaskCompleted(this);
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		Retentioner_Users.getInstance().load(_context);
		return Long.valueOf(0);
	}

	public boolean getIsFinished()
	{
		return _isFinished;
	}
}
