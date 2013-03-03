package com.Flanaria;

import twitter4j.Status;
import android.os.Bundle;

public class Activity_Conversation extends Activity_Twitter implements IActivity_Mention
{
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle("Loading...");
    }

	@Override
	protected void setViews()
	{
		super.setViews();

		AsyncTask_SetViews_Conversation task = new AsyncTask_SetViews_Conversation(this, CurrentTasks.getInstance().getCurrentConversationStatus());
		task.execute(getText(R.string.app_name).toString());
	}
	
	@Override
	public synchronized void doOperation()
	{
		Logger.d("Activity:DoOperation");
		if (_op == null)
		{
			Logger.d("Op is Null");
			ILateTapEventParent parent = this;
			_GestureListener.setLateTapEventParent(parent);
			return;
		}
		_CurrentStatus = new Status[]
		{ ((Adapter_Tweet) getListAdapter()).getItem(_pos) };

		AsyncTask_TapOperationExecuter task = new AsyncTask_TapOperationExecuter(
				this, _CurrentStatus, _op);
		task.execute(getString(R.string.app_name));
		Logger.d("Executed:");
		Logger.d("Exe:" + _CurrentStatus[0].getText());
		Logger.d("Exe:" + _op.toString());

		_op = null;
		_CurrentStatus = null;
	}
}
