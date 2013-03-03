package com.Flanaria;

import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class Listener_Post_OnTweet implements OnClickListener
{
	private Context _context;
	private String _tweet;
	private EditText _TweetBox;
	private long _inReplyToStatusId;
	private InputStream _img;

	public Listener_Post_OnTweet(Context context, EditText tweetBox, long inReplyToStatusId)
	{
		_context = context;
		_TweetBox = tweetBox;
		_inReplyToStatusId = inReplyToStatusId;
	}

	public void onClick(View v)
	{
		_tweet = _TweetBox.getText().toString();

		//コンバート処理とか

		//アクティブアカウントで投稿します。
		AsyncTask_PostTweet task = new AsyncTask_PostTweet(_context, _tweet, _inReplyToStatusId, _img);
		task.execute(_context.getText(R.string.app_name).toString());

		//親Activityを終了します
		((Activity)_context).finish();
	}

	public void setImageStream(InputStream in)
	{
		_img = in;
	}
}
