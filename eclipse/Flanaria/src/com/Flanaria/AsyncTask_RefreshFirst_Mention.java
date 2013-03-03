package com.Flanaria;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

public class AsyncTask_RefreshFirst_Mention extends AsyncTask<String, Integer, Long>
{
	private Context _context;

	public AsyncTask_RefreshFirst_Mention(Context context)
	{
		_context = context;
	}

	@Override
	protected void onPreExecute()
	{
	}

	@Override
	protected void onPostExecute(Long result)
	{
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		Observer_UserStream.getInstance().addActivityListener((Activity)_context);
		return Long.valueOf(0L);
	}
}
