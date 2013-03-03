package com.Flanaria;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

public class AsyncTask_CreateConversation extends AsyncTask<String, Integer, AlertDialog.Builder>
{
	private Context _context;
	private twitter4j.Status _status;

	public AsyncTask_CreateConversation(Context context, twitter4j.Status status)
	{
		_context = context;
		_status = status;
	}

	@Override
	protected void onPreExecute()
	{
	}

	@Override
	protected void onPostExecute(AlertDialog.Builder builder)
	{
		builder.create().show();
	}

	@Override
	protected AlertDialog.Builder doInBackground(String... arg0)
	{
		ArrayList<twitter4j.Status> _title = new ArrayList<twitter4j.Status>();
		_title.add(_status);
		ArrayList<twitter4j.Status> _list = Retentioner_Status.getInstance().getContainsList(_status);

		AlertDialog.Builder builder = new AlertDialog.Builder(_context);

		ListView title = new ListView(_context);
		title.setScrollingCacheEnabled(false);
		title.setBackgroundColor(_context.getResources().getColor(R.color.TweetBackGround0));
		Adapter_Tweet title_adapter = new Adapter_Tweet(_context, R.layout.tweet, _title);
		title.setAdapter(title_adapter);
		builder.setCustomTitle(title);

		ListView content = new ListView(_context);
		content.setScrollingCacheEnabled(false);
		content.setBackgroundColor(_context.getResources().getColor(R.color.TweetBackGround0));
		Adapter_Tweet content_adapter = new Adapter_Tweet(_context, R.layout.tweet, _list);
		content.setAdapter(content_adapter);
		builder.setView(content);

		builder.setPositiveButton(_context.getString(R.string.Positive), null);

		return builder;
	}
}
