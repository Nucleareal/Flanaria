package com.Flanaria;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;

import twitter4j.Status;
import twitter4j.UserStreamListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Activity_Post extends Activity_Twitter implements IAsyncTaskParent, UserStreamListener
{
	private EditText _TextBox;
	private TextView _CharacterCount;
	private LinearLayout _Layout;
	private long _inReplyToStatusId = -1;
	private Button _PostButton;
	private ImageButton _UploadImage;
	private ImageButton _Convert;
	private Listener_Post_OnTweet _listener;
	private ListView _ListView;
	private AsyncTask_ProgressDialog _dtask;
	SharedPreferences pref;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_SEARCH)
		{
			finish();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	protected void refreshes()
	{
		super.refreshes();
		
		if(Retentioner_Users.getInstance().isNotRefreshed())
		{
			_dtask = new AsyncTask_ProgressDialog(this, getString(R.string.Load_Users_Title), getString(R.string.Load_Users_Message));
			_dtask.execute("");
			AsyncTask_LoadUsers task = new AsyncTask_LoadUsers(this, this);
			task.execute("");
		}
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		
		if(pref.getBoolean(getString(R.string.Visiable_PostAct_NewElement_Key), false))
		{
			Observer_UserStream.getInstance().addActivityListener(this);
		}
	}

	public void onTaskCompleted(AsyncTask<?, ?, ?> task)
	{
		_dtask.stop();
	}

	@Override
	protected void setViews()
	{
		super.setViews();
		
		switch(Retentioner_Config.getInstance().getOrientation())
		{
			case 1 :
			{
				setContentView(R.layout.post_vertical);
				break;
			}
			case 2 :
			{
				setContentView(R.layout.post_horizonal);
				break;
			}
		}
		getViews();
		getIntentData();
		setListeners();
		
		Adapter_TwitterElement adp = new Adapter_TwitterElement(this, R.layout.tweet, new ArrayList<TwitterElement>());
		adp.setOmission(23);
		_ListView.setAdapter(adp);
	}

	private void getViews()
	{
		_PostButton = (Button)findViewById(R.id.Post_Button_PostButton);
		_UploadImage = (ImageButton)findViewById(R.id.Post_Button_UploadImage);
		_Convert = (ImageButton)findViewById(R.id.Post_Button_Convert);

		_TextBox = (EditText)findViewById(R.id.Post_EditText_PostText);
		_CharacterCount = (TextView)findViewById(R.id.Post_TextView_LeftCharacterCount);
		_Layout = (LinearLayout)findViewById(R.id.Post_LinearLayout_AddedState);
		_ListView = (ListView)findViewById(android.R.id.list);
		_Layout.removeAllViews();

		_PostButton.setEnabled(false);
	}

	private void setListeners()
	{
		_TextBox.requestFocus();
		_TextBox.addTextChangedListener(new Listener_Post_TextChanged(this, _TextBox, _PostButton, _CharacterCount));
		_listener = new Listener_Post_OnTweet(this, _TextBox, _inReplyToStatusId);
		_PostButton.setOnClickListener(_listener);
		_UploadImage.setOnClickListener(new Listener_UploadImage(this, _UploadImage));
		_Convert.setOnClickListener(new Listener_Convert(_TextBox));
	}

	private void getIntentData()
	{
		Intent intent = getIntent();
		if(intent != null)
		{
			Uri uri = intent.getData();
			String res;
			if(uri != null && (res = StatusUtils.getTextFromShareUri(uri)) != null)
			{
				refreshText(res);
			}

			Bundle extras = intent.getExtras();
			if (extras != null)
			{
				Logger.d("Intent has Extras:");
				Set<String> keySet = extras.keySet();
				for (String key : keySet)
				{
		            Logger.d(key + ": " + intent.getExtras().get(key));
		        }

				CharSequence ext = extras.getCharSequence(Intent.EXTRA_TEXT);
				if (ext != null)
				{
					refreshText(ext.toString());
				}
				long irtsi = extras.getLong("InReplyToStatusId");
				_inReplyToStatusId = irtsi;
				Logger.d("Getted:InReplyToStatusId:"+_inReplyToStatusId);
			}
		}
		
		String stt = "http://";
		for(int i = 0; stt.length() + 9 < 4096; i++)
			stt += "%e3%81%82";
		refreshText(stt);
	}

	private void refreshText(String res)
	{
		int clt = res.toString().length();

		_TextBox.setText(res);
		_PostButton.setEnabled(clt != 0);
		_CharacterCount.setText(String.valueOf(140-clt));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == 0 && resultCode == RESULT_OK)
		{
			try
			{
				InputStream in = getContentResolver().openInputStream(data.getData());
				_listener.setImageStream(in);
			}
			catch (Exception e)
			{
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		//finish();
		return true;
	}

	@Override
	public void onConfigurationChanged(Configuration conf)
	{
		super.onConfigurationChanged(conf);

		ViewGroup root = (ViewGroup)getWindow().getDecorView().findViewById(android.R.id.content);

		if(Retentioner_Config.getInstance().put(conf))
		{
			root.removeAllViews();

			setViews();
		}
	}
	
	@Override
	public void onStatus(Status status)
	{
		Logger.d("onStatus");
		Adapter_TwitterElement adp = ((Adapter_TwitterElement)_ListView.getAdapter());
		//adp.clear();
		adp.insert(new TwitterElement(status), 0);
		adp.notifyDataSetChanged();
	}
}
