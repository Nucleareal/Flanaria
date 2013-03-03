package com.Flanaria;

import twitter4j.DirectMessage;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;

public class Activity_DirectMessage extends Activity_Twitter implements IActivity_DirectMessage
{
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

	@Override
	protected void setViews()
	{
		super.setViews();

		AsyncTask_SetViews_DirectMessage task = new AsyncTask_SetViews_DirectMessage(this);
		task.execute(getText(R.string.app_name).toString());
	}

	@Override
	protected void refreshes()
	{
		super.refreshes();

		AsyncTask_RefreshFirst_DirectMessage task = new AsyncTask_RefreshFirst_DirectMessage(this);
		task.execute(getText(R.string.app_name).toString());
	}

	@Override
	public void onDirectMessage(DirectMessage msg)
	{
		DirectMessageUtils.insertMessage(this, (Adapter_DirectMessage)getListAdapter(), msg);
	}

	@Override
	public void onConfigurationChanged(Configuration conf)
	{
		super.onConfigurationChanged(conf);

		ViewGroup root = (ViewGroup)getWindow().getDecorView().findViewById(android.R.id.content);

		if(Retentioner_Config.getInstance().put(conf))
		{
			root.removeViewAt(1);

			addContentView(MenuLayoutCreator.getInstance().create(this, MenuLayoutCreator.Values.Main), MenuLayoutCreator.getInstance().FILL());
		}
	}
}
