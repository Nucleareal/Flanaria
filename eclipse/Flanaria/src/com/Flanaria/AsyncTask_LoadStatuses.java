package com.Flanaria;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.os.AsyncTask;


public class AsyncTask_LoadStatuses extends AsyncTask<String, Integer, List<ArrayList<TwitterElement>>>
{
	private List<ArrayList<TwitterElement>> _lists;
	private int _cursor;
	private IAsyncTaskParent _parent;

	public AsyncTask_LoadStatuses(IAsyncTaskParent parent, int cursor)
	{
		_parent = parent;
		_cursor = cursor;
	}

	@Override
	protected void onPreExecute()
	{
	}

	@Override
	protected void onPostExecute(List<ArrayList<TwitterElement>> result)
	{
		_lists = result;
		_parent.onTaskCompleted(this);
	}

	@Override
	protected List<ArrayList<TwitterElement>> doInBackground(String... arg0)
	{
		List<ArrayList<TwitterElement>> result = new ArrayList<ArrayList<TwitterElement>>();
		result.add(new ArrayList<TwitterElement>());
		result.add(new ArrayList<TwitterElement>());
		result.add(new ArrayList<TwitterElement>());
		List<twitter4j.Status> list_S = Retentioner_Users.getInstance().getAllUsersStatus();
		List<twitter4j.Status> list_M = Retentioner_Users.getInstance().getAllUsersMention();
		List<twitter4j.DirectMessage> list_D = Retentioner_Users.getInstance().getAllUsersDirectMessage();
		for(twitter4j.Status status : list_M)
		{
			result.get(0).add(new TwitterElement(status));
		}
		for(twitter4j.Status status : list_S)
		{
			result.get(1).add(new TwitterElement(status));
		}
		for(twitter4j.DirectMessage msg : list_D)
		{
			result.get(2).add(new TwitterElement(msg));
		}
		return result;
	}
	
	public List<ArrayList<TwitterElement>> getResult()
	{
		return _lists;
	}
	
	public int getCursor()
	{
		return _cursor;
	}
}
