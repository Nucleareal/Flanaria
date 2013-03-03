package com.Flanaria;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.widget.ListView;

public class AsyncTask_SetViews_Conversation extends AsyncTask<String, Integer, List<twitter4j.Status>>
{
	private ListActivity _context;
	private twitter4j.Status _status;
	private Adapter_Tweet _adapter;

	public AsyncTask_SetViews_Conversation(ListActivity context, twitter4j.Status status)
	{
		_context = context;
		_status = status;
	}

	@Override
	protected void onPreExecute()
	{
		ListView listview = _context.getListView();
		_adapter = new Adapter_Tweet(_context, R.layout.tweet, new ArrayList<twitter4j.Status>());
		_adapter.setNotifyOnChange(true);
		listview.setAdapter(_adapter);
		_context.setContentView(listview);
		_context.setListAdapter(_adapter);
		_context.getListView().setAdapter(_adapter);

		/*_context.getListView().setFastScrollEnabled(true);
		_adapter = new TweetAdapter(_context, R.layout.tweet, new ArrayList<twitter4j.Status>());
		_context.getListView().setAdapter(_adapter);
		_adapter.setNotifyOnChange(true);
		_context.setContentView(_context.getListView());

		_context.setListAdapter(_adapter);*/
	}

	private ArrayList<twitter4j.Status> getList()
	{
		return Retentioner_Status.getInstance().getContainsList(_status);
	}

	@Override
	protected void onPostExecute(List<twitter4j.Status> list)
	{
		for(twitter4j.Status status : list)
		{
			StatusUtils.insertStatus(_context, _adapter, status);
		}
		/*ArrayList<twitter4j.Status> list = getList();

		for(twitter4j.Status status : list)
		{
			_adapter.insert(status, 0);
			((TweetAdapter)_context.getListAdapter()).insert(status, 0);
		}*/

		_context.setTitle("Show Conversation");
	}

	@Override
	protected List<twitter4j.Status> doInBackground(String... arg0)
	{
		return getList();
	}
}
