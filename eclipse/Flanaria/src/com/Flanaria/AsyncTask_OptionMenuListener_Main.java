package com.Flanaria;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageButton;

public class AsyncTask_OptionMenuListener_Main extends AsyncTask<String, Integer, Long>
{
	private Context _context;
	private int _itemId;
	private IStreamButtonParent _parent;

	public AsyncTask_OptionMenuListener_Main(Context context, int itemId, IStreamButtonParent parent)
	{
		_context = context;
		_itemId = itemId;
		_parent = parent;
	}

	@Override
	protected void onPreExecute()
	{
	}

	@Override
	protected void onPostExecute(Long result)
	{
		switch(_itemId)
		{
			case R.id.Menu_Main_Settings :
			{
				IntentUtils.intent((Activity)_context, "Activity_Preference");
				break;
			}
			case R.id.Menu_Main_Stream_Change :
			{
				ImageButton b = CurrentTasks.getInstance().getStreamButton();
				if(_parent.getIsDestroyButton())
				{
					Observer_UserStream.getInstance().destroyAllStreams();
					b.setImageDrawable(_context.getResources().getDrawable(R.drawable.navigation_refresh));
					b.setOnClickListener(CurrentTasks.getInstance().getRESTListener());
				}
				else
				{
					Retentioner_Users.getInstance().connectAllUserStreams();
					b.setImageDrawable(_context.getResources().getDrawable(R.drawable.av_pause));
					b.setOnClickListener(CurrentTasks.getInstance().getStreamListener());
				}
				break;
			}
			case R.id.Menu_Main_AddAccounts :
			{
				IntentUtils.intent((Activity)_context, "Activity_Authorize");
				break;
			}
			case R.id.Menu_Main_Search :
			{
				IntentUtils.intent((Activity)_context, "Activity_ScreenNameSearch");
			}
			default :
			{
				break;
			}
		}
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		return Long.valueOf(0L);
	}
}
