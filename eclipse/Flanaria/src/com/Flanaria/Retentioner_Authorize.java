package com.Flanaria;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;

public class Retentioner_Authorize
{
	private Enum_AuthorizeState _state;
	private WebViewClient_Authorize _authorizeView;
	private WebChromeClient_Authorize _authorizeChrome;
	private Twitter _twitter;
	private RequestToken _token;
	private AccessToken _atoken;
	private String _pin;
	private Activity _parent;
	private User _user;

	public Retentioner_Authorize()
	{
		_twitter = new TwitterFactory().getInstance();
		_state = Enum_AuthorizeState.Not_Authorized;
	}

	public void setAuthorizeClient(WebViewClient_Authorize view)
	{
		_authorizeView = view;
	}
	public WebViewClient_Authorize getAuthorizeClient()
	{
		return _authorizeView;
	}

	public void setPin(String str)
	{
		_pin = str;
	}
	public String getPin()
	{
		return _pin;
	}

	public void setTwitter(Twitter twitter)
	{
		_twitter = twitter;
	}
	public Twitter getTwitter()
	{
		return _twitter;
	}

	public void setRequestToken(RequestToken token)
	{
		_token = token;
	}
	public RequestToken getRequestToken()
	{
		return _token;
	}

	public void setAccessToken(AccessToken atoken)
	{
		_atoken = atoken;
	}
	public AccessToken getAccessToken()
	{
		return _atoken;
	}

	public void setAuthorizeChrome(WebChromeClient_Authorize achrome)
	{
		_authorizeChrome = achrome;
	}
	public WebChromeClient_Authorize getAuthorizeChrome()
	{
		return _authorizeChrome;
	}

	public void setState(Enum_AuthorizeState state)
	{
		_state = state;
	}
	public Enum_AuthorizeState getState()
	{
		return _state;
	}

	public void setParentActivity(Activity parent)
	{
		_parent = parent;
	}
	public Activity getParentActivity()
	{
		return _parent;
	}

	public void setUser(User user)
	{
		_user = user;
	}
	public User getUser()
	{
		return _user;
	}
}
