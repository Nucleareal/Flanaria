package com.Flanaria;

import java.util.ArrayList;

import twitter4j.User;
import android.app.Activity;

public class AutoComplete_Users
{
	private ArrayList<String> _users;

	private String _autor = "Flanaria_autor";

	public void refresh(Activity parent)
	{
		_users = new ArrayList<String>();
		_users.add(_autor);

		AsyncTask_LoadFollowScreenName task = new AsyncTask_LoadFollowScreenName(parent);
		task.execute("");
	}

	public void parse(ArrayList<User> list)
	{
		for(User u : list)
		{
			if(!u.getScreenName().equals(_autor))
			{
				_users.add(u.getScreenName());
			}
		}
	}

	public ArrayList<String> getAllUsersScreenName()
	{
		return _users;
	}

	//
	private AutoComplete_Users()
	{
	}
	private static final AutoComplete_Users _ins = new AutoComplete_Users();
	public static AutoComplete_Users getInstance()
	{
		return _ins;
	}
}
