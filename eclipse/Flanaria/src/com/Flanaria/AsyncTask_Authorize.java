package com.Flanaria;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

public class AsyncTask_Authorize extends AsyncTask<String, Integer, Long>
{
	private Context _context;
	private String _text;

	public AsyncTask_Authorize(Context context)
	{
		_context = context;
	}

	@Override
	protected void onPostExecute(Long result)
	{
		Retentioner_Toast.getInstance().showToast((Activity)_context, _text, false);

		CurrentTasks.getInstance().getCurrentAuthorize().setState(Enum_AuthorizeState.Completed);
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		_text = _context.getResources().getString(R.string.Authorize_Failed);
		try
		{
			Retentioner_Authorize auth = CurrentTasks.getInstance().getCurrentAuthorize();
			Twitter twitter = auth.getTwitter();
			auth.setAccessToken(twitter.getOAuthAccessToken(auth.getRequestToken(), auth.getPin()));
			twitter.setOAuthAccessToken(auth.getAccessToken());
			auth.setUser(twitter.verifyCredentials());

			Retentioner_Users.getInstance().save(_context, auth);

			_text = ReplaceUtils.replaceUserNames(_context.getResources().getString(R.string.Authorize_Success), auth);
		}
		catch(TwitterException e)
		{
			Logger.e(e);
		}
		return Long.valueOf(0);
	}
}
