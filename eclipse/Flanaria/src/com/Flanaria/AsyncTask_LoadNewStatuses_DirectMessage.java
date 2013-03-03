package com.Flanaria;

import java.util.List;

import twitter4j.DirectMessage;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.widget.ImageButton;

public class AsyncTask_LoadNewStatuses_DirectMessage extends AsyncTask<String, Integer, Long> implements ILayoutValue
{
	private ListActivity _context;
	private ImageButton _button;
	private List<DirectMessage> _list;
	private IAsyncTaskParent _parent;

	public AsyncTask_LoadNewStatuses_DirectMessage(ImageButton button, ListActivity context, IAsyncTaskParent parent)
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
		for(twitter4j.DirectMessage msg : _list)
		{
			DirectMessageUtils.insertMessage(_context, (Adapter_DirectMessage)_context.getListAdapter(), msg);
		}
		_button.setEnabled(true);
		_parent.onTaskCompleted(this);
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		_list = Retentioner_Users.getInstance().getAllUsersDirectMessage();
		return Long.valueOf(0);
	}
}
