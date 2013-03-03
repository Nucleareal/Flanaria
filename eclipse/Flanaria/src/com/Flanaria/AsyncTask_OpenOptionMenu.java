package com.Flanaria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

public class AsyncTask_OpenOptionMenu extends AsyncTask<String, Integer, Long> implements OnItemSelectedListener, OnItemClickListener
{
	private Activity _parent;
	private twitter4j.Status _status;
	private AlertDialog.Builder _builder;
	private AlertDialog _dialog;
	private ListView _listView;
	private ArrayList<ContextMenuItem> _list;
	private AsyncTask_ProgressDialog _diag;

	public AsyncTask_OpenOptionMenu(Activity parent, twitter4j.Status status, AsyncTask_ProgressDialog diag)
	{
		_parent = parent;
		_status = status;
		_diag = diag;
	}

	@Override
	protected void onPostExecute(Long result)
	{
		_listView.setOnItemClickListener(this);
		_listView.setOnItemSelectedListener(this);
		_dialog = _builder.create();
		_dialog.show();
		_diag.stop();
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		_builder = new AlertDialog.Builder(_parent);

		//Titleを作ります
		ListView titleListView = new ListView(_parent);
		ArrayList<twitter4j.Status> titleList = new ArrayList<twitter4j.Status>();
		titleList.clear();
		Adapter_Tweet adapter = new Adapter_Tweet(_parent, R.layout.tweet, titleList);
		titleListView.setAdapter(adapter);
		adapter.insert(_status, 0);
		titleListView.setBackgroundColor(_parent.getResources().getColor(R.color.TweetBackGround0));
		titleListView.setScrollingCacheEnabled(false);
		titleListView.setBackgroundColor(_parent.getResources().getColor(R.color.List_BackColor));

		//Menuの中身を作ります
		_listView = new ListView(_parent);
		_list = new ArrayList<ContextMenuItem>();
		_list.clear();
		Enum_ContextMenu[] def = Enum_ContextMenu.getDefaultValues();
		for(int i = 0; i < def.length; i++)
		{
			_list.add(new ContextMenuItem(_parent, def[i], _status));
		}
		if(_status.isRetweet())
		{
			addExtras(_status.getRetweetedStatus());
		}
		addExtras(_status);

		ContextMenuAdapter _adapter = new ContextMenuAdapter(_parent, R.layout.menuitem, _list);
		_listView.setAdapter(_adapter);

		_listView.setBackgroundColor(_parent.getResources().getColor(R.color.List_BackColor));
		_listView.setScrollingCacheEnabled(false);

		_builder.setCustomTitle(titleListView);
		_builder.setView(_listView);

		return Long.valueOf(0);
	}
	//URL User HashTag

	private void addExtras(twitter4j.Status status)
	{
		//URL
		String[] urls = StatusUtils.getURLAndMediaEntitiesAsArray(status);
		if(urls != null)
		{
			for(String s : urls)
			{
				_list.add(new ContextMenuItem(_parent, Enum_ContextMenu.URL, status, s));
			}
		}
		//Conversation
		if(Enum_ContextMenu.ShowConversation.isShouldShow(status))
		{
			_list.add(new ContextMenuItem(_parent, Enum_ContextMenu.ShowConversation, status));
		}
		//User
		HashMap<String, ContextMenuItem> map = new HashMap<String, ContextMenuItem>();
		map.clear();
		map.put(status.getUser().getScreenName(), new ContextMenuItem(_parent, Enum_ContextMenu.User, status, "@"+status.getUser().getScreenName()));
		String[] mentions = StatusUtils.getScreenNameEntities(status);
		if(mentions != null)
		{
			for(String s : mentions)
			{
				map.put(s, new ContextMenuItem(_parent, Enum_ContextMenu.User, status, "@"+s));
			}
		}
		Set<String> keys = map.keySet();
		for(String key : keys)
		{
			_list.add(map.get(key));
		}
		//HashTag
		String[] hashtags = StatusUtils.getHashTagEntities(status);
		if(hashtags != null)
		{
			for(String s : hashtags)
			{
				_list.add(new ContextMenuItem(_parent, Enum_ContextMenu.HashTag, status, "#"+s));
			}
		}
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id)
	{
		Logger.d("ContextMenu:onItemSelected["+position+"]");
		_dialog.dismiss();
		_list.get(position).onSelected();
	}

	public void onNothingSelected(AdapterView<?> arg0)
	{
		Logger.d("onNothingSelected");
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		onItemSelected(arg0, arg1, arg2, arg3);
	}
}
