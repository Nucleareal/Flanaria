package com.Flanaria;

import android.content.Context;
import android.os.AsyncTask;

public class AsyncTask_SaveData extends AsyncTask<String, Integer, Long>
{
	private Context _context;

	public AsyncTask_SaveData(Context context)
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
		IconCache.clearCache();
		Retentioner_Users.getInstance().destroyAllUserStreams();
		com.Flanaria.MuteQuery.Easy.Retentioner_Query.getInstance().save(_context);
		com.Flanaria.MuteQuery.Lunatic.Retentioner_Query.getInstance().save(_context);
		return Long.valueOf(0L);
	}
}
