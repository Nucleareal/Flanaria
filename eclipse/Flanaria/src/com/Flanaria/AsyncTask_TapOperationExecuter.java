package com.Flanaria;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

public class AsyncTask_TapOperationExecuter extends AsyncTask<String, Integer, Boolean>
{
	private Context _context;
	private twitter4j.Status[] _Statuses;
	private Enum_TweetTapOperation _op;

	public AsyncTask_TapOperationExecuter(Context context, twitter4j.Status[] statuses, Enum_TweetTapOperation op)
	{
		_context = context;
		_op = op;
		_Statuses = statuses;
	}

	@Override
	protected void onPreExecute()
	{
	}

	@Override
	protected void onPostExecute(Boolean isShould)
	{
		if(isShould)
		{
			doOperation();
		}
	}

	@Override
	protected Boolean doInBackground(String... arg0)
	{
		boolean isShould = true;
		if(_op.isCanBackGround())
		{
			doOperation();
			isShould = false;
		}
		return Boolean.valueOf(isShould);
	}

	private void doOperation()
	{
		if(_Statuses == null)
		{
			return;
		}
		for(twitter4j.Status status : _Statuses)
		{
			try
			{
				_op.operation(status, (Activity)_context);
			}
			catch(Exception e)
			{
				Logger.e(e);
			}
		}
	}
}
