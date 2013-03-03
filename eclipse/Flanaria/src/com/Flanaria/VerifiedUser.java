package com.Flanaria;

import twitter4j.Twitter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserStreamListener;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class VerifiedUser implements IFlanariaConsumer
{
	private Twitter _twitter;
	private User _user;
	private String _userName;
	private String _userScreenName;
	private AccessToken _token;
	private String _token_key;
	private String _token_secret;
	private long _userId;
	private boolean _isActive;
	private TwitterStream _stream;
	private VerifiedUser _fallUser;

	public VerifiedUser(Twitter twitter, User user, AccessToken token)
	{
		_twitter = twitter;
		_user = user;
		_token = token;
		_userName = _user.getName();
		_userScreenName = _user.getScreenName();
		_token_key = _token.getToken();
		_token_secret = _token.getTokenSecret();
		_userId = _token.getUserId();
		_isActive = false;
	}

	public void renew(User renewUser)
	{
		_userName = renewUser.getName();
		_userScreenName = renewUser.getScreenName();
	}

	public static boolean equalUsers(VerifiedUser usra, VerifiedUser usrb)
	{
		if(usra == null)
		{
			return usrb == null;
		}
		return usra.equals(usrb);
	}

	@Override
	public boolean equals(Object o)
	{
		if(o instanceof VerifiedUser)
		{
			if(o != null)
			{
				return Long.valueOf(getId()).equals(Long.valueOf(((VerifiedUser)o).getId()));
			}
		}
		return false;
	}

	public Twitter getTwitter()
	{
		return _twitter;
	}

	public String getName()
	{
		return _userName;
	}

	public String getScreenName()
	{
		return _userScreenName;
	}

	public long getId()
	{
		return _userId;
	}

	public String getToken()
	{
		return _token_key;
	}

	public String getTokenSecret()
	{
		return _token_secret;
	}

	public void setActive(boolean flag)
	{
		_isActive = flag;
	}
	public boolean getActive()
	{
		return _isActive;
	}

	public void setFallBackToUser(VerifiedUser user)
	{
		_fallUser = user;
	}
	public VerifiedUser getFallBackToUser()
	{
		return _fallUser;
	}
	public long getFallBackToUserId()
	{
		VerifiedUser user = getFallBackToUser();
		if(user == null)
		{
			return -1L;
		}
		return user.getId();
	}

	public void connectUserStream(UserStreamListener observer)
	{
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(_CONSUMER_KEY_);
		builder.setOAuthConsumerSecret(_CONSUMER_SECRET_);
		builder.setOAuthAccessToken(getToken());
		builder.setOAuthAccessTokenSecret(getTokenSecret());
		builder.setUserStreamRepliesAllEnabled(false);
		Configuration config = builder.build();
		_stream = (new TwitterStreamFactory(config)).getInstance(_token);
		_stream.addListener(observer);
		_stream.user();
	}

	public void destroyStream()
	{
		if(_stream != null)
		{
			_stream.shutdown();
			_stream.cleanUp();
			_stream = null;
		}
	}
}
