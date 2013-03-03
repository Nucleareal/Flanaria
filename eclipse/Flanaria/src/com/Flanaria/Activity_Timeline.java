package com.Flanaria;

import java.util.Date;
import java.util.HashMap;

import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.User;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.Flanaria.SunMoon.MoonCalcurator;
import com.Flanaria.SunMoon.SunCalcurator;
import com.Flanaria.SunMoon.SunRoundData;

public class Activity_Timeline extends Activity_Twitter implements IActivity_Timeline
{
	private HashMap<AsyncTask<?, ?, ?>, AsyncTask_ProgressDialog> _map;
	private ImageButton _Stream;
	private LocationManager locationManager;
	private boolean _isDestroyButton;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_SEARCH)
		{
			IntentUtils.intent(this, "Activity_Search");
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	protected void setViews()
	{
		_map = new HashMap<AsyncTask<?, ?, ?>, AsyncTask_ProgressDialog>();

		super.setViews();

		AsyncTask_SetViews_Main task = new AsyncTask_SetViews_Main(this, this);
		showProgress(task, getString(R.string.Refresh_Content_Title), getString(R.string.Refresh_Content_Message));
		task.execute(getText(R.string.app_name).toString());
	}

	@Override
	protected void refreshes()
	{
		super.refreshes();

		Retentioner_Config.getInstance().put(getResources().getConfiguration());

		locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new Listener_LocationChanged(locationManager, this));

		AsyncTask_RefreshFirst_Main task = new AsyncTask_RefreshFirst_Main(this, this);
		showProgress(task, getString(R.string.Refresh_Title), getString(R.string.Refresh_Message));
		task.execute(getText(R.string.app_name).toString());
	}

	@Override
	public void onConfigurationChanged(Configuration conf)
	{
		super.onConfigurationChanged(conf);

		ViewGroup root = (ViewGroup)getWindow().getDecorView().findViewById(android.R.id.content);

		if(Retentioner_Config.getInstance().put(conf))
		{
			root.removeViewAt(1);

			addContentView(MenuLayoutCreator.getInstance().create(this, MenuLayoutCreator.Values.Main), MenuLayoutCreator.getInstance().FILL());
		}
	}

	private void showProgress(AsyncTask<?, ?, ?> keyTask, String title, String message)
	{
		AsyncTask_ProgressDialog task = new AsyncTask_ProgressDialog(this, title, message);
		_map.put(keyTask, task);
		task.execute("");
	}

	private void hideProgress(AsyncTask<?, ?, ?> keyTask)
	{
		if(_map.containsKey(keyTask))
		{
			_map.get(keyTask).stop();
		}
	}

	private void loadUsers()
	{
		//ユーザーを読み込みます。
		AsyncTask_LoadUsers task = new AsyncTask_LoadUsers(this, this);
		showProgress(task, getResources().getString(R.string.Timeline_LoadUser_Title), getResources().getString(R.string.Timeline_LoadUser_Message));
		task.execute(getResources().getString(R.string.app_name));
	}

	public void onTaskCompleted(AsyncTask<?, ?, ?> task)
	{
		hideProgress(task);
		if(task instanceof AsyncTask_SetViews_Main)
		{
			_Stream = ((AsyncTask_SetViews_Main)task).getResultImageButton();
			CurrentTasks.getInstance().setStreamButton(_Stream);
		}
		if(task instanceof AsyncTask_LoadUsers)
		{
			onLoadUserCompleted();
		}
		/*if(task instanceof AsyncTask_SaveData)
		{
			hideProgress(task);
			super.onDestroy();
		}*/
		if(task instanceof AsyncTask_RefreshFirst_Main)
		{
			loadUsers();
		}
		if(task instanceof AsyncTask_LoadFirstStatuses)
		{
		}
		return;
	}

	public void onLoadUserCompleted()
	{
		//もしUserが一人もいなかったらAuthorizeActivityに誘導します
		if(Retentioner_Users.getInstance().isNotAvailableUser())
		{
			AsyncTask_Intent_Authorize task = new AsyncTask_Intent_Authorize(this);
			task.execute(getResources().getString(R.string.app_name));
		}
		//ストリーミングを開始します。
		AsyncTask_LoadFirstStatuses task = new AsyncTask_LoadFirstStatuses(this, this, _Stream);
		//showProgress(task, getText(R.string.Timeline_LoadStatus_Title).toString(), getText(R.string.Timeline_LoadStatus_Message).toString());
		task.execute(getResources().getString(R.string.app_name));
	}

	@Override
	protected void onPause()
	{
		Retentioner_Users.getInstance().save(this);
		super.onPause();
	}

	@Override
	protected void onDestroy()
	{
		AsyncTask_SaveData task = new AsyncTask_SaveData(this);
		task.execute(getText(R.string.app_name).toString());
		super.onDestroy();
	}

	@Override
	public void onStatus(Status status)
	{
		StatusUtils.insertStatus(this, (Adapter_Tweet)getListAdapter(), status);
		
		if(Wrapper_SharedPreference.getInstance().getBoolean(this, "NotifySettings_Category_Available") && StatusUtils.isMentionToMe(status))
		{
			NotifyDispatcher.getInstance().dispatch(this, new NotifyElement(new TwitterElement(status), Enum_NotifyType.MentionRecived));
		}
	}
	
	@Override
	public void onFavorite(User arg0, User arg1, Status arg2)
	{
		if(Wrapper_SharedPreference.getInstance().getBoolean(this, "NotifySettings_Category_Available"))
		{
			NotifyDispatcher.getInstance().dispatch(this, new NotifyElement(arg0, arg1, new TwitterElement(arg2)));
		}
	}
	
	@Override
	public void onDirectMessage(DirectMessage arg0)
	{
		if(Wrapper_SharedPreference.getInstance().getBoolean(this, "NotifySettings_Category_Available"))
		{
			NotifyDispatcher.getInstance().dispatch(this, new NotifyElement(new TwitterElement(arg0), Enum_NotifyType.DirectMessageRecived));
		}
	}
	
	public void onRetweet(User arg0, User arg1, Status arg2)
	{
		if(Wrapper_SharedPreference.getInstance().getBoolean(this, "NotifySettings_Category_Available"))
		{
			NotifyDispatcher.getInstance().dispatch(this, new NotifyElement(new TwitterElement(arg2), Enum_NotifyType.RetweetRecived));
		}
	}
	
	/*public void onFollow(User arg0, User arg1)
	{
		if(Wrapper_SharedPreference.getInstance().getBoolean(this, "NotifySettings_Category_Available"))
		{
			NotifyDispatcher.getInstance().dispatch(this, new NotifyElement(new Twi));
		}
	}*/

	public void onUserProfileUpdate(User user)
	{
		Retentioner_Users.getInstance().onUserUpdate(user);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		Logger.d("onCreateOptionsMenu");
		
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		AsyncTask_OptionMenuListener_Main task = new AsyncTask_OptionMenuListener_Main(this, item.getItemId(), this);
		task.execute(getResources().getText(R.string.app_name).toString());
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		Logger.d("onPrepareOptionsMenu");
		
		super.onPrepareOptionsMenu(menu);
		MenuItem c = (MenuItem)menu.findItem(R.id.Menu_Main_Stream_Change);
		if(Observer_UserStream.getInstance().getIsStreaming())
		{
			c.setIcon(getResources().getDrawable(R.drawable.av_stop));
			c.setTitle(R.string.Menu_Main_Stream_Title_Disconnect);
			_isDestroyButton = true;
		}
		else
		{
			c.setIcon(getResources().getDrawable(R.drawable.av_play));
			c.setTitle(R.string.Menu_Main_Stream_Title_Connect);
			_isDestroyButton = false;
		}
		return true;
	}

	public boolean getIsDestroyButton()
	{
		return _isDestroyButton;
	}

	public void onLocation(Location location)
	{
		SunRoundData data = SunCalcurator.SunMoon(location);
		Retentioner_Lovely.getInstance().onSunData(data);
		Logger.d(String.valueOf(MoonCalcurator.getMoonAge(new Date())));
		locationManager = null;
	}
}
