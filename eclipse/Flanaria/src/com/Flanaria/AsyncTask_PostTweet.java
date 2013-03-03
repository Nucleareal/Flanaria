package com.Flanaria;

import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

public class AsyncTask_PostTweet extends AsyncTask<String, Integer, Long>
{
	private Context _context;
	private String _text;
	private long _inReplyToStatusId;
	private List<String> _list;
	private InputStream _img;

	public AsyncTask_PostTweet(Context context, String text, long inReplyToStatusId, InputStream img)
	{
		_context = context;
		_text = text;
		_inReplyToStatusId = inReplyToStatusId;
		_img = img;
	}

	@Override
	protected void onPreExecute()
	{
	}

	@Override
	protected void onPostExecute(Long result)
	{
		ToastUtils.showAsListOrFalse((Activity)_context, _context.getString(R.string.Post_Posted), _list, false);
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		try
		{
			Logger.d("AsyncTask:InReplyToStatusId:"+_inReplyToStatusId);
			_list = Retentioner_Users_Proxy.getInstance().postStatusOnActive(_text, _inReplyToStatusId, _img);
		}
		catch(NoAccountsActiveException e)
		{
			try
			{
				Retentioner_Toast.getInstance().showToast((Activity)_context, _context.getString(R.string.NoAccountsActive), false);
			}
			catch(Exception e1)
			{
				Logger.e(e1);
			}
		}
		return Long.valueOf(0L);
	}
}
