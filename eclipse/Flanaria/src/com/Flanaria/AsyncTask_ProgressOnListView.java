package com.Flanaria;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class AsyncTask_ProgressOnListView extends AsyncTask<String, Integer, Long> implements ILayoutValue
{
	private Context _context;
	private ProgressBar _progress;
	protected boolean _isFinished;
	private LinearLayout _header;

	public AsyncTask_ProgressOnListView(Context context, LinearLayout header)
	{
		_context = context;
		_header = header;
	}

	@Override
	protected void onPreExecute()
	{
		_isFinished = false;
		LinearLayout progressL = (LinearLayout)((Activity)_context).getLayoutInflater().inflate(R.layout.progressbar, null);
		_progress = (ProgressBar)progressL.findViewById(R.id.ProgressBar_Bar);
		_progress.setVisibility(View.VISIBLE);
		_header.removeAllViews();
		_header.addView(progressL, new AbsListView.LayoutParams(FILL, WRAP));
	}

	@Override
	protected void onPostExecute(Long result)
	{
		_header.removeAllViews();
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		while(!_isFinished)
		{
			try
			{
				Thread.sleep(1);
			}
			catch(InterruptedException e)
			{
				break;
			}
		}
		return Long.valueOf(0);
	}

	public void stop()
	{
		_isFinished = true;
	}
}
