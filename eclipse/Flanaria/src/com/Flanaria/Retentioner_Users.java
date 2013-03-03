package com.Flanaria;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import twitter4j.DirectMessage;
import twitter4j.IDs;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Context;

public class Retentioner_Users implements IFlanariaConsumer, IReplaceStrings
{
	private Retentioner_Users()
	{
	}
	private static Retentioner_Users _ins;
	static
	{
		_ins = new Retentioner_Users();
	}
	public static Retentioner_Users getInstance()
	{
		return _ins;
	}

	private ArrayList<VerifiedUser> _users;
	private int apicounter;
	private List<ObjectPair<VerifiedUser, Long>> _fallSetting;
	int loadc;

	public boolean isNotRefreshed()
	{
		if(_users == null)
		{
			return true;
		}
		return isNotAvailableUser();
	}

	public void refresh()
	{
		_users = new ArrayList<VerifiedUser>();
		_users.clear();
		_fallSetting = new ArrayList<ObjectPair<VerifiedUser, Long>>();
		apicounter = -1;
	}

	public void save(Context context)
	{
		for(int i = 0; i < _users.size(); i++)
		{
			VerifiedUser user = _users.get(i);
			Wrapper_SharedPreference.getInstance().putString(context, context.getResources().getString(R.string.SP_Accounts_TokenA).replace(NUMBER, Integer.valueOf(i).toString()), user.getToken());
			Wrapper_SharedPreference.getInstance().putString(context, context.getResources().getString(R.string.SP_Accounts_TokenB).replace(NUMBER, Integer.valueOf(i).toString()), user.getTokenSecret());
			//アクティブ状態のセーブ
			Wrapper_SharedPreference.getInstance().putBoolean(context,
					context.getResources().getString(R.string.SP_Accounts_Active).replace(NUMBER, String.valueOf(i)),
					user.getActive());
			//FallBackToのセーブ
			Wrapper_SharedPreference.getInstance().putLong(context,
					context.getResources().getString(R.string.SP_Accounts_FallBackTo).replace(NUMBER, String.valueOf(i)),
					user.getFallBackToUserId());
		}
		Logger.d("Loadc:"+loadc);
		Wrapper_SharedPreference.getInstance().putString(context, context.getResources().getString(R.string.SP_Accounts_TokenA).replace(NUMBER, String.valueOf(loadc)), null);
		Wrapper_SharedPreference.getInstance().putString(context, context.getResources().getString(R.string.SP_Accounts_TokenB).replace(NUMBER, String.valueOf(loadc)), null);
	}

	public void save(Context context, Retentioner_Authorize auth)
	{
		add(new VerifiedUser(auth.getTwitter(), auth.getUser(), auth.getAccessToken()));
		loadc++;
		Logger.d("User Added. Loadc:"+loadc);
		save(context);
	}

	public void load(Context context)
	{
		loadc = 0;

		for(int i = 0; ; i++)
		{
			//ユーザーを読み込みます
			String key = Wrapper_SharedPreference.getInstance().getString(context, context.getResources().getString(R.string.SP_Accounts_TokenA).replace(NUMBER, Integer.valueOf(i).toString()), null);
			String sec = Wrapper_SharedPreference.getInstance().getString(context, context.getResources().getString(R.string.SP_Accounts_TokenB).replace(NUMBER, Integer.valueOf(i).toString()), null);
			Logger.d("Key:"+key);
			Logger.d("Sec:"+sec);
			if(key == null || key.equals(sec))
			{
				break;
			}

			Logger.d("User Loaded. Loadc:"+loadc);
			loadc++;

			//VerifiedUserのインスタンス生成と登録
			VerifiedUser account = null;
			try
			{
				ConfigurationBuilder builder = new ConfigurationBuilder();
				builder.setOAuthConsumerKey(_CONSUMER_KEY_);
				builder.setOAuthConsumerSecret(_CONSUMER_SECRET_);
				builder.setOAuthAccessToken(key);
				builder.setOAuthAccessTokenSecret(sec);
				Configuration config = builder.build();
				Twitter twitter = (new TwitterFactory(config)).getInstance();
				User user = twitter.verifyCredentials();
				account = new VerifiedUser(twitter, user, twitter.getOAuthAccessToken());

				add(account);
			}
			catch(Exception e)
			{
				Logger.e(e);
				continue;
			}
			//各種ユーザーデータ読み込み
			account.setActive(Wrapper_SharedPreference.getInstance().getBoolean(context, context.getResources().getString(R.string.SP_Accounts_Active).replace(NUMBER, String.valueOf(i)), false));
			enqueueFallBackSetting(account, Wrapper_SharedPreference.getInstance().getLong(context,
					context.getResources().getString(R.string.SP_Accounts_FallBackTo).replace(NUMBER, String.valueOf(i))
					, -1L));
		}
		dequeueFallBackSetting();
		logs();

		AutoComplete_Users.getInstance().refresh((Activity)context);
	}

	private void logs()
	{
		for(VerifiedUser user : _users)
		{
			if(user.getFallBackToUser() != null)
			{
				Logger.d("Flan.Load @"+user.getScreenName()+" FallTo @"+user.getFallBackToUser().getScreenName());
			}
			Logger.d("Flan.Load @"+user.getScreenName()+" is"+(user.getActive() ? "" : " Not")+" Active");
		}
	}

	private void dequeueFallBackSetting()
	{
		for(ObjectPair<VerifiedUser, Long> obj : _fallSetting)
		{
			VerifiedUser user1 = obj.getValue1();
			Long v2 = obj.getValue2();
			VerifiedUser user2 = forId(v2.longValue());
			user1.setFallBackToUser(user2);
			if(user2 != null)
			{
				Logger.d("Flan.FallLoad:@"+user1.getScreenName()+" Set To @:"+user2.getScreenName());
			}
		}
	}

	private void enqueueFallBackSetting(VerifiedUser from, long toId)
	{
		_fallSetting.add(new ObjectPair<VerifiedUser, Long>(from, Long.valueOf(toId)));
	}

	private void add(VerifiedUser user)
	{
		_users.add(user);
	}

	public boolean isNotAvailableUser()
	{
		return _users.size() <= 0;
	}

	public void destroyAll(Context context)
	{
		_users.clear();
		save(context);
	}

	public List<Status> getAllUsersStatus()
	{
		ArrayList<Status> list = new ArrayList<Status>();
		list.clear();
		for(VerifiedUser user : _users)
		{
			Twitter twitter = user.getTwitter();
			{
				try
				{
					ResponseList<Status> rlist = twitter.getHomeTimeline();
					for(Status s : rlist)
					{
						list.add(s);
					}
				}
				catch(TwitterException e)
				{
					Logger.e(e);
				}
			}
		}
		return list;
	}

	public List<twitter4j.Status> getAllUsersMention()
	{
		ArrayList<Status> list = new ArrayList<Status>();
		list.clear();
		for(VerifiedUser user : _users)
		{
			Twitter twitter = user.getTwitter();
			{
				try
				{
					ResponseList<Status> rlist = twitter.getMentionsTimeline();
					for(Status s : rlist)
					{
						list.add(s);
					}
				}
				catch(TwitterException e)
				{
					Logger.e(e);
				}
			}
		}
		return list;
	}

	public List<DirectMessage> getAllUsersDirectMessage()
	{
		ArrayList<DirectMessage> list = new ArrayList<DirectMessage>();
		list.clear();
		for(VerifiedUser user : _users)
		{
			Twitter twitter = user.getTwitter();
			{
				try
				{
					ResponseList<DirectMessage> rlist = twitter.getDirectMessages();
					for(DirectMessage s : rlist)
					{
						list.add(s);
					}
				}
				catch(TwitterException e)
				{
					Logger.e(e);
				}
			}
		}
		return list;
	}


	public void connectAllUserStreams()
	{
		for(VerifiedUser user : _users)
		{
			Observer_UserStream.getInstance().addStreamUser(user);
		}
	}

	public void destroyAllUserStreams()
	{
		Observer_UserStream.getInstance().destroyAllStreams();
	}

	public Twitter getAPITwitter()
	{
		return _users.get((++apicounter) % _users.size()).getTwitter();
	}

	public List<ObjectPair<String, Boolean>> getAllUserActiveSets()
	{
		ArrayList<ObjectPair<String, Boolean>> list = new ArrayList<ObjectPair<String, Boolean>>();
		list.clear();
		for(VerifiedUser user : _users)
		{
			list.add(new ObjectPair<String, Boolean>(user.getScreenName(), Boolean.valueOf(user.getActive())));
		}
		return list;
	}

	public List<ObjectPair<String, VerifiedUser>> getAllUsetFallbackToSets()
	{
		ArrayList<ObjectPair<String, VerifiedUser>> list = new ArrayList<ObjectPair<String, VerifiedUser>>();
		list.clear();
		for(VerifiedUser user : _users)
		{
			list.add(new ObjectPair<String, VerifiedUser>(user.getScreenName(), user.getFallBackToUser()));
		}
		return list;
	}

	public void setUserIsActive(String name, boolean isActive)
	{
		VerifiedUser user = forName(name);
		if(user != null)
		{
			user.setActive(isActive);
		}
	}

	public List<String> actionOnActive(IActionOnUser actionValue, Status status, String text, long inReplyToStatusId, InputStream img) throws NoAccountsActiveException
	{
		ArrayList<String> list = new ArrayList<String>();
		boolean isAvailableUser = false;
		for(VerifiedUser user : _users)
		{
			String s = actionOnActiveOnUser(actionValue, status, text, inReplyToStatusId, user, img, false);
			if(s != null)
			{
				list.add(s);
			}
			isAvailableUser = true;
		}
		if(!isAvailableUser)
		{
			throw new NoAccountsActiveException();
		}
		return list;
	}

	private String actionOnActiveOnUser(IActionOnUser actionValue, Status status, String text, long inReplyToStatusId, VerifiedUser user, InputStream img, boolean isFallback)
	{
		if(user.getActive() || isFallback)
		{
			Logger.d("Flan.Post:@"+user.getScreenName());
			Twitter twitter = user.getTwitter();
			try
			{
				actionValue.action(status, text, inReplyToStatusId, twitter, user, img);
				return user.getScreenName();
			}
			catch(TwitterException e)
			{
				Logger.d("Flan.Post:Failed");
				if((e.getStatusCode() == 400 || e.getStatusCode() == 403) && user.getFallBackToUser() != null)
				{
					Logger.d("Flan.Post:Retry");
					actionOnActiveOnUser(actionValue, status, text, inReplyToStatusId, user.getFallBackToUser(), img, true);
				}
				Logger.e(e);
			}
		}
		return null;
	}

	private String getAllUserNamesWithAttAsFlag(boolean isAllFlag)
	{
		String s = "";
		for(VerifiedUser user : _users)
		{
			if(isAllFlag || user.getActive())
			{
				s += "@"+user.getScreenName()+" ";
			}
		}
		return s;
	}

	public String getAllUserNamesWithAtt()
	{
		return getAllUserNamesWithAttAsFlag(true);
	}

	public String getAllActiveUserNamesWithAtt()
	{
		return getAllUserNamesWithAttAsFlag(false);
	}

	public void setFallBackRelationship(String fromScreenName, String toScreenName)
	{
		VerifiedUser from = forName(fromScreenName);
		VerifiedUser to = forName(toScreenName);
		from.setFallBackToUser(to);
	}

	private VerifiedUser forId(long id)
	{
		for(VerifiedUser user : _users)
		{
			if(user.getId() == id)
			{
				return user;
			}
		}
		return null;
	}

	public VerifiedUser forName(String screenName)
	{
		for(VerifiedUser user : _users)
		{
			if(user.getScreenName().equals(screenName))
			{
				return user;
			}
		}
		return null;
	}

	public void onUserUpdate(User renewUser)
	{
		for(VerifiedUser user : _users)
		{
			if(user.getId() == renewUser.getId())
			{
				user.renew(renewUser);
				return;
			}
		}
		IconUtils.refresh(renewUser);
	}

	public boolean isUserScreenName(String name)
	{
		VerifiedUser user = forName(name);
		return user != null;
	}

	public ArrayList<User> getAllUsersFollowUsers()
	{
		ArrayList<User> result = new ArrayList<User>();

		HashMap<Long, Object> shows = new HashMap<Long, Object>();
		for(VerifiedUser user : _users)
		{
			long cursor = -1L;
			IDs ids = null;
	        do
	        {
	        	try
	        	{
	        		ids = user.getTwitter().getFriendsIDs(user.getId(), cursor);
	        		for (long id : ids.getIDs())
		            {
		            	shows.put(Long.valueOf(id), null);
		            }
	        	}
	        	catch(Exception e)
	        	{
	        	}
	            cursor = ids.getNextCursor();
	        }
	        while (ids.hasNext());
		}
		Set<Long> set = shows.keySet();
		Long[] array = set.toArray(new Long[0]);
		long[] conva = new long[array.length];
		for(int i = 0; i < array.length; i++)
		{
			conva[i] = array[i].longValue();
		}
		List<long[]> lst = ConnectUtils.splitLongArray(conva, 100);
		for(long[] ary : lst)
		{
			Twitter twitter = getAPITwitter();
			try
			{
				ResponseList<User> list = twitter.lookupUsers(ary);
				for(User u : list)
				{
					result.add(u);
				}
			}
			catch(TwitterException e)
			{
			}
		}
		return result;
	}
}
