package com.Flanaria;

import java.util.LinkedList;
import java.util.Queue;

import android.os.AsyncTask;
import android.view.MotionEvent;

public class AsyncTask_MotionEventWait extends AsyncTask<String, Integer, Long>
{
	private IMotionEventDispatcher _parent;
	private long _waitTime;
	private Queue<MotionEvent> _queue;

	public AsyncTask_MotionEventWait(IMotionEventDispatcher parent)
	{
		_parent = parent;
	}

	@Override
	protected void onPreExecute()
	{
		_queue = new LinkedList<MotionEvent>();
	}

	@Override
	protected void onPostExecute(Long result)
	{
		_parent.dispatchMotionEvent(_queue);
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		while(_waitTime < 1000L)
		{
			try
			{
				Thread.sleep(1);
			}
			catch(InterruptedException e)
			{
				_waitTime = -1L;
			}
			_waitTime++;
		}
		return Long.valueOf(0L);
	}

	public void onEvent(MotionEvent e)
	{
		_waitTime = -1L;
	}
}
