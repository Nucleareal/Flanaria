package com.Flanaria;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MenuLayoutCreator implements IAsyncTaskParent
{
	public interface Values
	{
		int Main 			= 15; //1111
		int Mention			= 11; //1011
		int DirectMessage	= 13; //1101
	}

	private LinearLayout _ButtonLayout;
	private ImageButton _button;
	private OnClickListener REST;
	private OnClickListener STREAM;
	private AsyncTask_ProgressOnListView _dtask;

	public View create(Activity act, int param)
	{
		LayoutInflater inflater = act.getLayoutInflater();
        switch(Retentioner_Config.getInstance().getOrientation())
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
        setListeners(act);
		return _ButtonLayout;
	}

	private void setListeners(Activity act)
	{
		final Activity _act = act;
		final IAsyncTaskParent statusParent = this;
		((ImageButton)_ButtonLayout.findViewById(R.id.Buttons_PostButton)).setOnClickListener(
		new OnClickListener()
		{
			public void onClick(View v)
			{
				IntentUtils.intent(_act, "Activity_Post");
			}
		});
		((ImageButton)_ButtonLayout.findViewById(R.id.Buttons_MentionButton)).setOnClickListener(
		new OnClickListener()
		{
			public void onClick(View v)
			{
				IntentUtils.intent(_act, "Activity_Mention");
			}
		});
		((ImageButton)_ButtonLayout.findViewById(R.id.Buttons_DirectMailButton)).setOnClickListener(
		new OnClickListener()
		{
			public void onClick(View v)
			{
				IntentUtils.intent(_act, "Activity_DirectMessage");
			}
		});
		_button = ((ImageButton)_ButtonLayout.findViewById(R.id.Buttons_FetchButton));
		REST = new OnClickListener()
		{
			public void onClick(View v)
			{
				Logger.d("Loading new Statuses...");
				_dtask = new AsyncTask_ProgressOnListView(_act, ((Activity_Twitter)_act).getHeader());
				_dtask.execute(_act.getText(R.string.app_name).toString());
				AsyncTask_LoadNewStatuses_Main task = new AsyncTask_LoadNewStatuses_Main((ImageButton)v, (ListActivity)_act, statusParent);
				task.execute(_act.getText(R.string.app_name).toString());
			}
		};
		STREAM = new View.OnClickListener()
		{
			public void onClick(View v)
			{
				if(!Observer_UserStream.getInstance().getIsPause())
				{
					_button.setImageDrawable(_act.getResources().getDrawable(R.drawable.av_play));
					Retentioner_Toast.getInstance().showToast((Activity)_act, _act.getString(R.string.Stream_Pause), false);
				}
				else
				{
					_button.setImageDrawable(_act.getResources().getDrawable(R.drawable.av_pause));
					Retentioner_Toast.getInstance().showToast((Activity)_act, _act.getString(R.string.Stream_Restart), false);
				}
				Observer_UserStream.getInstance().reversePauseState();
			}
		};
		((ImageButton)_ButtonLayout.findViewById(R.id.Buttons_FetchButton)).setOnClickListener(REST);
		CurrentTasks.getInstance().setRESTListener(REST);
		CurrentTasks.getInstance().setStreamListener(STREAM);
		CurrentTasks.getInstance().setStreamButton(_button);
		if(Observer_UserStream.getInstance().getIsStreaming())
		{
			Retentioner_Users.getInstance().connectAllUserStreams();
			_button.setImageDrawable(_act.getResources().getDrawable(R.drawable.av_pause));
			_button.setOnClickListener(CurrentTasks.getInstance().getStreamListener());
		}
	}

	public LayoutParams FILL()
	{
		return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
	}

	public void onTaskCompleted(AsyncTask<?, ?, ?> task)
	{
		_dtask.stop();
	}

	//

	private MenuLayoutCreator()
	{
	}
	private static final MenuLayoutCreator _ins = new MenuLayoutCreator();
	public static MenuLayoutCreator getInstance()
	{
		return _ins;
	}
}
