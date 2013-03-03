package com.Flanaria;

import twitter4j.Status;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;

public class Activity_Mention extends Activity_Twitter implements IActivity_Mention
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

		AsyncTask_SetViews_Mention task = new AsyncTask_SetViews_Mention(this);
		task.execute(getText(R.string.app_name).toString());
	}

	@Override
	protected void refreshes()
	{
		super.refreshes();

		AsyncTask_RefreshFirst_Mention task = new AsyncTask_RefreshFirst_Mention(this);
		task.execute(getText(R.string.app_name).toString());
	}

	@Override
	public void onStatus(Status status)
	{
		if(StatusUtils.isMentionToMe(status))
		{
			StatusUtils.insertStatus(this, (Adapter_Tweet)getListAdapter(), status);
		}
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
