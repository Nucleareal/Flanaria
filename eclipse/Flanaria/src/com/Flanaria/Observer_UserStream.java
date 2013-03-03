package com.Flanaria;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;
import android.app.Activity;

public class Observer_UserStream implements UserStreamListener
{
	private Observer_UserStream()
	{
	}
	private static Observer_UserStream _instance;
	static
	{
		_instance = new Observer_UserStream();
	}
	public static Observer_UserStream getInstance()
	{
		return _instance;
	}

	private List<Activity> _listenerActivities;
	private List<UserStreamListener> _listeners;
	private List<VerifiedUser> _users;
	private boolean _isStopped;
	private Queue<Status> _queue;
	private volatile boolean _isStreaming;

	public void refresh()
	{
		_users = new ArrayList<VerifiedUser>();
		_listeners = new ArrayList<UserStreamListener>();
		_listenerActivities = new ArrayList<Activity>();
		_queue = new LinkedList<Status>();
	}

	public void addStreamUser(VerifiedUser user)
	{
		_users.add(user);
		user.connectUserStream(this);
		_isStreaming = true;
	}

	public void removeStreamUser(VerifiedUser user)
	{
		if(_users.contains(user))
		{
			_users.remove(user);
			user.connectUserStream(this);
		}
		if(_users.isEmpty())
		{
			_isStreaming = false;
		}
	}

	public void addListener(UserStreamListener listener)
	{
		_listeners.add(listener);
	}

	public void addActivityListener(Activity listener) throws ClassCastException
	{
		_listenerActivities.add(listener);
	}

	public void removeActivityListener(Activity listener)
	{
		_listenerActivities.remove(listener);
	}

	public void destroyAllStreams()
	{
		for(VerifiedUser user : _users)
		{
			user.destroyStream();
		}
		_isStreaming = false;
		_isStopped = false;
	}

	public boolean getIsStreaming()
	{
		return _isStreaming;
	}

	public void reversePauseState()
	{
		_isStopped = !_isStopped;
		if(!_isStopped)
		{
			restart();
		}
	}

	public boolean getIsPause()
	{
		return _isStopped;
	}

	private void restart()
	{
		openStatuses();
	}

	private void openStatuses()
	{
		while(!_queue.isEmpty())
		{
			onStatus(_queue.poll());
		}
	}

	//Status

	public void onStatus(final Status status)
	{
		if(_isStopped)
		{
			_queue.offer(status);
			return;
		}
		for(final Activity acv : _listenerActivities)
		{
			acv.runOnUiThread(new Runnable()
			{
				public void run()
				{
					((UserStreamListener)acv).onStatus(status);
				}
			});
		}
		Retentioner_Status.getInstance().onStatus(status);
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
}
