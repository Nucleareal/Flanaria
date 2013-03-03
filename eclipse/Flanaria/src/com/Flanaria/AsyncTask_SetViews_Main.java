package com.Flanaria;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class AsyncTask_SetViews_Main extends AsyncTask<String, Integer, Long> implements ILayoutValue, IAsyncTaskParent
{
	private IAsyncTaskParent _parent;
	private ListActivity _context;
	private LinearLayout _ButtonLayout;
	private LinearLayout _header;
	private AsyncTask_ProgressOnListView _dtask;
	private ImageButton _button;
	private OnClickListener REST;
	private OnClickListener STREAM;

	public AsyncTask_SetViews_Main(ListActivity context, IAsyncTaskParent parent)
	{
		_context = context;
		_parent = parent;
		_header = new LinearLayout(_context);
		_header.setGravity(Gravity.CENTER);
		((Activity_Twitter)_context).setHeader(_header);
	}

	@Override
	protected void onPreExecute()
	{
		_context.getListView().addHeaderView(_header);
		_context.getListView().setLongClickable(true);
		_context.setListAdapter(new Adapter_Tweet(_context, R.layout.tweet, new ArrayList<twitter4j.Status>()));
        ((Adapter_Tweet)_context.getListAdapter()).setNotifyOnChange(true);
        LayoutInflater inflater = _context.getLayoutInflater();
        switch(_context.getResources().getConfiguration().orientation)
        {
        	default :
        	case 1 :
        	{
        		_ButtonLayout = (LinearLayout)inflater.inflate(R.layout.buttons_vertical, null);
        		break;
        	}
        	case 2 :
        	{
            	_ButtonLayout = (LinearLayout)inflater.inflate(R.layout.buttons_horizonal_right, null);
            	break;
        	}
        }
        _context.setContentView(_context.getListView());
        _context.addContentView(_ButtonLayout, new LinearLayout.LayoutParams(FILL, FILL));
        _context.getListView().setFastScrollEnabled(true);
	}

	@Override
	protected void onPostExecute(Long result)
	{
		final IAsyncTaskParent statusParent = this;
		((ImageButton)_ButtonLayout.findViewById(R.id.Buttons_PostButton)).setOnClickListener(
		new OnClickListener()
		{
			public void onClick(View v)
			{
				IntentUtils.intent(_context, "Activity_Post");
			}
		});
		((ImageButton)_ButtonLayout.findViewById(R.id.Buttons_MentionButton)).setOnClickListener(
		new OnClickListener()
		{
			public void onClick(View v)
			{
				IntentUtils.intent(_context, "Activity_Mention");
			}
		});
		((ImageButton)_ButtonLayout.findViewById(R.id.Buttons_DirectMailButton)).setOnClickListener(
		new OnClickListener()
		{
			public void onClick(View v)
			{
				IntentUtils.intent(_context, "Activity_DirectMessage");
			}
		});
		((ImageButton)_ButtonLayout.findViewById(R.id.Buttons_FetchButton)).setOnClickListener(REST);
		_button = ((ImageButton)_ButtonLayout.findViewById(R.id.Buttons_FetchButton));
		REST = new OnClickListener()
		{
			public void onClick(View v)
			{
				Logger.d("Loading new Statuses...");
				_dtask = new AsyncTask_ProgressOnListView(_context, _header);
				_dtask.execute(_context.getText(R.string.app_name).toString());
				AsyncTask_LoadNewStatuses_Main task = new AsyncTask_LoadNewStatuses_Main((ImageButton)v, _context, statusParent);
				task.execute(_context.getText(R.string.app_name).toString());
			}
		};
		STREAM = new View.OnClickListener()
		{
			public void onClick(View v)
			{
				if(!Observer_UserStream.getInstance().getIsPause())
				{
					_button.setImageDrawable(_context.getResources().getDrawable(R.drawable.av_play));
					Retentioner_Toast.getInstance().showToast((Activity)_context, _context.getString(R.string.Stream_Pause), false);
				}
				else
				{
					_button.setImageDrawable(_context.getResources().getDrawable(R.drawable.av_pause));
					Retentioner_Toast.getInstance().showToast((Activity)_context, _context.getString(R.string.Stream_Restart), false);
				}
				Observer_UserStream.getInstance().reversePauseState();
			}
		};
		CurrentTasks.getInstance().setRESTListener(REST);
		CurrentTasks.getInstance().setStreamListener(STREAM);
		_parent.onTaskCompleted(this);
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		return Long.valueOf(0);
	}

	public void onTaskCompleted(AsyncTask<?, ?, ?> task)
	{
		_dtask.stop();
	}

	public ImageButton getResultImageButton()
	{
		return _button;
	}
}
