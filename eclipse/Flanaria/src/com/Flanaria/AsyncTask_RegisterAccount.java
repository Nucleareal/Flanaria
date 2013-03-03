package com.Flanaria;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewDatabase;

public class AsyncTask_RegisterAccount extends AsyncTask<String, Integer, Long> implements IFlanariaConsumer
{
	private Context _context;
	private Retentioner_Authorize _auth;
	private WebView _webView;
	private IAsyncTaskParent _parent;

	public AsyncTask_RegisterAccount(Context context, Retentioner_Authorize auth, IAsyncTaskParent parent)
	{
		_context = context;
		_auth = auth;
		_parent = parent;
	}

	@Override
	protected void onPreExecute()
	{
	}

	@Override
	protected void onPostExecute(Long result)
	{
		_parent.onTaskCompleted(this);
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		_auth = new Retentioner_Authorize();
		_auth.setParentActivity((Activity)_context);
		CurrentTasks.getInstance().setCurrentAuthorize(_auth);

		Twitter twitter = _auth.getTwitter();
		twitter.setOAuthConsumer(_CONSUMER_KEY_, _CONSUMER_SECRET_);
		try
		{
			_auth.setRequestToken(twitter.getOAuthRequestToken());
		}
		catch(TwitterException e)
		{
			Logger.e(e);
		}

		WebViewClient_Authorize aview = new WebViewClient_Authorize();
		_auth.setAuthorizeClient(aview);
		WebChromeClient_Authorize achrome = new WebChromeClient_Authorize();
		_auth.setAuthorizeChrome(achrome);

		_webView = (WebView)((Activity)_context).findViewById(R.id.Authorize_WebView);
		clearWebViewData();
		_webView.requestFocus(View.FOCUS_DOWN);
		_webView.setWebViewClient(_auth.getAuthorizeClient());
		_webView.getSettings().setJavaScriptEnabled(true);
		try
		{
			_webView.loadUrl(_auth.getRequestToken().getAuthenticationURL());
		}
		catch(Exception e)
		{
			Logger.e(e);
		}
		_webView.setWebChromeClient(_auth.getAuthorizeChrome());
		return Long.valueOf(0L);
	}

	private void clearWebViewData()
	{
		_webView.clearCache(true);
		_webView.clearFormData();
		_webView.clearHistory();
		_webView.clearSslPreferences();
		_webView.clearView();
		CookieManager.getInstance().removeAllCookie();
		WebViewDatabase.getInstance(_context).clearFormData();
		WebViewDatabase.getInstance(_context).clearHttpAuthUsernamePassword();
		WebViewDatabase.getInstance(_context).clearUsernamePassword();
	}

	public Retentioner_Authorize getRetentionerAuth()
	{
		return _auth;
	}

	public WebView getWebView()
	{
		return _webView;
	}
}
