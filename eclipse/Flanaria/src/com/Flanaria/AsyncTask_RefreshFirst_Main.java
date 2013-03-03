package com.Flanaria;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.Flanaria.SunMoon.MoonCalcurator;

public class AsyncTask_RefreshFirst_Main extends AsyncTask<String, Integer, Long>
{
	private Context _context;
	private IAsyncTaskParent _parent;

	public AsyncTask_RefreshFirst_Main(Context context, IAsyncTaskParent parent)
	{
		_context = context;
		_parent = parent;
	}

	@Override
	protected void onPreExecute()
	{
		Logger.d("Refreshing...");
	}

	@Override
	protected void onPostExecute(Long result)
	{
		Logger.d("All Refreshed");
		_parent.onTaskCompleted(this);
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		int c = 0;
		Logger.d("Do In Background...");
		Retentioner_Users.getInstance().refresh(); Logger.d("Refreshed:"+c++);
		Observer_UserStream.getInstance().refresh(); Logger.d("Refreshed:"+c++);
		Retentioner_Status.getInstance().refresh(); Logger.d("Refreshed:"+c++);
		com.Flanaria.MuteQuery.Easy.Retentioner_Query.getInstance().refresh(); Logger.d("Refreshed:"+c++);
		com.Flanaria.MuteQuery.Lunatic.Retentioner_Query.getInstance().refresh(); Logger.d("Refreshed:"+c++);
		com.Flanaria.MuteQuery.Easy.Retentioner_Query.getInstance().load(_context); Logger.d("Refreshed:"+c++);
		com.Flanaria.MuteQuery.Lunatic.Retentioner_Query.getInstance().load(_context); Logger.d("Refreshed:"+c++);
		Retentioner_Lovely.getInstance().refresh(); Logger.d("Refreshed:"+c++);
		Retentioner_Lovely.getInstance().load(_context); Logger.d("Refreshed:"+c++);
		Retentioner_Lovely.getInstance().onMoonData(MoonCalcurator.getMoonAge(new Date())); Logger.d("Refreshed:"+c++);
		CurrentTasks.getInstance().load(_context); Logger.d("Refreshed:"+c++);
		Observer_UserStream.getInstance().addActivityListener((Activity)_context); Logger.d("Refreshed:"+c++);
		return Long.valueOf(0L);
	}
}
