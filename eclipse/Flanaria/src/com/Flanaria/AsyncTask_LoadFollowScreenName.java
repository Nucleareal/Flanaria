package com.Flanaria;

import java.util.ArrayList;

import twitter4j.User;
import android.app.Activity;
import android.os.AsyncTask;

public class AsyncTask_LoadFollowScreenName extends AsyncTask<String, Integer, Long>
{
	private Activity _parent;

	public AsyncTask_LoadFollowScreenName(Activity parent)
	{
		_parent = parent;
	}

	@Override
	protected void onPreExecute()
	{
	}

	@Override
	protected void onPostExecute(Long result)
	{
		Retentioner_Toast.getInstance().showToast(_parent, _parent.getString(R.string.Load_Friends_Completed), true);
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		ArrayList<User> list = Retentioner_Users.getInstance().getAllUsersFollowUsers();
		AutoComplete_Users.getInstance().parse(list);
		return Long.valueOf(0);
	}
}
