package com.Flanaria;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

public class Activity_Search extends Activity_Twitter implements OnClickListener, IAsyncTaskParent
{
	private EditText _edit;
	private ImageButton _button;
	private ListView _list;
	private LinearLayout _head;
	private AsyncTask_ProgressDialog _dtask;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void setViews()
	{
		super.setViews();
		setContentView(R.layout.search);
		
		_list = (ListView)findViewById(android.R.id.list);
		_button = (ImageButton)findViewById(R.id.Search_Button);
		_edit = (EditText)findViewById(R.id.Search_Text);
		_head = new LinearLayout(this);
		
		_list.addHeaderView(_head);
		
		Adapter_TwitterElement adapter = new Adapter_TwitterElement(this, R.layout.tweet, new ArrayList<TwitterElement>());
		_list.setAdapter(adapter);
		setListAdapter(adapter);
		
		_button.setOnClickListener(this);
	}
	
	@Override
	protected void refreshes()
	{
		if(Retentioner_Users.getInstance().isNotRefreshed())
		{
			_dtask = new AsyncTask_ProgressDialog(this, getString(R.string.Load_Users_Title), getString(R.string.Load_Users_Message));
			_dtask.execute("");
			AsyncTask_LoadUsers task = new AsyncTask_LoadUsers(this, (IAsyncTaskParent)this);
			task.execute("");
		}
		
		super.refreshes();
	}

	public void onClick(View v)
	{
		(new AsyncTask_Search(this, _edit.getText().toString(), _head)).execute("");
	}

	public void onTaskCompleted(AsyncTask<?, ?, ?> task)
	{
		if(task instanceof AsyncTask_LoadUsers)
		{
			_dtask.stop();
		}
		if(task instanceof AsyncTask_Search)
		{
			List<Status> list = ((AsyncTask_Search)task).getResult();
			Adapter_TwitterElement adp = (Adapter_TwitterElement)getListAdapter();
			for(Status status : list)
			{
				Utils_TwitterElement.insert(adp, status);
			}
		}
	}
}
