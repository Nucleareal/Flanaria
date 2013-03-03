package com.Flanaria;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

public class AsyncTask_OnContextMenuSelected extends AsyncTask<String, Integer, Long>
{
	private Context _context;
	private twitter4j.Status _status;
	private String _extra;
	private Enum_ContextMenu _item;

	public AsyncTask_OnContextMenuSelected(Enum_ContextMenu item, Context context, twitter4j.Status status, String extra)
	{
		_context = context;
		_status = status;
		_extra = extra;
		_item = item;
	}

	@Override
	protected void onPreExecute()
	{
	}

	@Override
	protected void onPostExecute(Long l)
	{
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		_item.onSelected((Activity)_context, _status, _extra);
		return null;
	}
}
