package com.Flanaria;

import java.lang.reflect.Constructor;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.User;
import twitter4j.UserList;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater.Factory;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class Activity_Twitter extends ListActivity implements IActivity_Twitter
{
	private GestureDetector _GestureDetector;
	protected Listener_GestureOnListActivity _GestureListener;
	protected Enum_TweetTapOperation _op;
	protected int _pos;
	protected Status[] _CurrentStatus;
	private LinearLayout _head;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setViews();
		refreshes();
	}

	@Override
	protected void onDestroy()
	{
		Observer_UserStream.getInstance().removeActivityListener(this);
		super.onDestroy();
	}

	protected void setViews()
	{
		ensureInflaterFactory();
		getListView().setBackgroundColor(getResources().getColor(R.color.List_BackColor));

		_GestureListener = new Listener_GestureOnListActivity(this, this);
		_GestureDetector = new GestureDetector(_GestureListener);
		getListView().setOnItemSelectedListener(this);
		getListView().setOnItemLongClickListener(this);
		getListView().setOnItemClickListener(this);
		getListView().setOnTouchListener(this);
	}

	protected void refreshes()
	{
	}

	public void onStatus(Status status)
	{
	}

	public void onDeletionNotice(StatusDeletionNotice arg0)
	{
	}

	public void onScrubGeo(long arg0, long arg1)
	{
	}

	public void onStallWarning(StallWarning arg0)
	{
	}

	public void onTrackLimitationNotice(int arg0)
	{
	}

	public void onException(Exception arg0)
	{
	}

	public void onBlock(User arg0, User arg1)
	{
	}

	public void onDeletionNotice(long arg0, long arg1)
	{
	}

	public void onDirectMessage(DirectMessage arg0)
	{
	}

	public void onFavorite(User arg0, User arg1, Status arg2)
	{
	}

	public void onFollow(User arg0, User arg1)
	{
	}

	public void onFriendList(long[] arg0)
	{
	}

	public void onRetweet(User arg0, User arg1, Status arg2)
	{
	}

	public void onUnblock(User arg0, User arg1)
	{
	}

	public void onUnfavorite(User arg0, User arg1, Status arg2)
	{
	}

	public void onUserListCreation(User arg0, UserList arg1)
	{
	}

	public void onUserListDeletion(User arg0, UserList arg1)
	{
	}

	public void onUserListMemberAddition(User arg0, User arg1, UserList arg2)
	{
	}

	public void onUserListMemberDeletion(User arg0, User arg1, UserList arg2)
	{
	}

	public void onUserListSubscription(User arg0, User arg1, UserList arg2)
	{
	}

	public void onUserListUnsubscription(User arg0, User arg1, UserList arg2)
	{
	}

	public void onUserListUpdate(User arg0, UserList arg1)
	{
	}

	public void onUserProfileUpdate(User arg0)
	{
	}

	public void setCurrentTapEvent(Enum_TweetTapOperation op)
	{
		_op = op;
	}

	public boolean onTouch(View v, MotionEvent event)
	{
		super.onTouchEvent(event);
		//Logger.d("Activity:onTouch");
		//Logger.d("View:" + v.getClass());
		return _GestureDetector.onTouchEvent(event);
	}

	@Override
	protected void onListItemClick(ListView listView, View v, int pos, long id)
	{
		super.onListItemClick(listView, v, pos, id);
		Logger.d("Activity:onListItemClick");
		_pos = pos;
		doOperation();
		return;
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3)
	{
		Logger.d("Activity:onItemClick");
		_pos = pos;
		doOperation();
		return;
	}

	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos,
			long arg3)
	{
		Logger.d("Activity:onItemLongClick");
		_pos = pos;
		doOperation();
		return false;
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
			long arg3)
	{
		Logger.d("Activity:onItemSelected");
		_pos = pos;
		doOperation();
		return;
	}

	public synchronized void doOperation()
	{
		Logger.d("Activity:DoOperation");
		if (_op == null)
		{
			Logger.d("Op is Null");
			ILateTapEventParent parent = this;
			_GestureListener.setLateTapEventParent(parent);
			return;
		}
		_CurrentStatus = new Status[]
		{ ((Adapter_Tweet) getListAdapter()).getItem(_pos - 1) };

		AsyncTask_TapOperationExecuter task = new AsyncTask_TapOperationExecuter(
				this, _CurrentStatus, _op);
		task.execute(getString(R.string.app_name));
		Logger.d("Executed:");
		Logger.d("Exe:" + _CurrentStatus[0].getText());
		Logger.d("Exe:" + _op.toString());

		_op = null;
		_CurrentStatus = null;
	}

	public void onNothingSelected(AdapterView<?> arg0)
	{
	}

	public void setHeader(LinearLayout header)
	{
		_head = header;
	}

	public LinearLayout getHeader()
	{
		return _head;
	}
	
	void ensureInflaterFactory()
	{
		final Factory Lfactory = getLayoutInflater().getFactory();
		if(Lfactory == null)
		{
			final Factory factory = new Factory()
			{
				public View onCreateView(String name, Context context, AttributeSet attrs)
				{
					if(name.equals("com.android.internal.view.menu.IconMenuView"))
					{
						try
						{
							ClassLoader cl = getClassLoader();
							Class<?> clazz;
							clazz = cl.loadClass("com.android.internal.view.menu.IconMenuView");
							Constructor<?> constructor = clazz.getConstructor(Context.class, AttributeSet.class);
							Object view = constructor.newInstance(context, attrs);
							ViewGroup vg = (ViewGroup)view;
							vg.setBackgroundColor(Color.parseColor("#7F7F0000"));
							return vg;
						}
						catch(Exception e)
						{
						}
					}
					return null;
				}
			};
			getLayoutInflater().setFactory(factory);
		}
	}
}
