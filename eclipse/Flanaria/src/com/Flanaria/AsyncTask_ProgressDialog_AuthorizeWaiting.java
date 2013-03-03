package com.Flanaria;

import android.content.Context;

public class AsyncTask_ProgressDialog_AuthorizeWaiting extends AsyncTask_ProgressDialog
{
	public AsyncTask_ProgressDialog_AuthorizeWaiting(Context context, String title, String message)
	{
		super(context, title, message);
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		while(!CurrentTasks.getInstance().getCurrentAuthorize().getState().equals(Enum_AuthorizeState.Completed))
		{
		}
		stop();
		return Long.valueOf(0);
	}

	public boolean getIsFinished()
	{
		return _isFinished;
	}
}
