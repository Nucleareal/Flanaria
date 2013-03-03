package com.Flanaria;

import twitter4j.User;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class AsyncTask_LoadUserIcon extends AsyncTask<String, Integer, Long>
{
	private Bitmap _ic;
	//private Drawable _icon;
	private ImageView _imageView;
	private boolean _isFinished;
	private User _user;

	public AsyncTask_LoadUserIcon(Context context, ImageView imageView, User user)
	{
		_imageView = imageView;
		_user = user;
	}

	@Override
	protected void onPostExecute(Long result)
	{
		_imageView.setImageBitmap(_ic);
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		_ic = IconUtils.getUserIcon(_user);
		return Long.valueOf(0);
	}

	public boolean getIsFinished()
	{
		return _isFinished;
	}
}
