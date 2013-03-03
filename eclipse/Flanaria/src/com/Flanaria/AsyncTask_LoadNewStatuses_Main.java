package com.Flanaria;

import java.util.List;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.widget.ImageButton;

public class AsyncTask_LoadNewStatuses_Main extends AsyncTask<String, Integer, Long> implements ILayoutValue
{
	private ListActivity _context;
	private ImageButton _button;
	private List<twitter4j.Status> _list;
	private IAsyncTaskParent _parent;

	public AsyncTask_LoadNewStatuses_Main(ImageButton button, ListActivity context, IAsyncTaskParent parent)
	{
		_context = context;
		_button = button;
		_parent = parent;
	}

	@Override
	protected void onPreExecute()
	{
		_button.setEnabled(false);
	}

	@Override
	protected void onPostExecute(Long result)
	{
		for(twitter4j.Status status : _list)
		{
			StatusUtils.insertStatus(_context, (Adapter_Tweet)_context.getListAdapter(), status);
		}
		_button.setEnabled(true);
		_parent.onTaskCompleted(this);
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		_list = Retentioner_Users.getInstance().getAllUsersStatus();
		return Long.valueOf(0);
	}
}
