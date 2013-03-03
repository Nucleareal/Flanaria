package com.Flanaria;

import android.os.AsyncTask;

public interface IAsyncTaskParent
{
	public void onTaskCompleted(AsyncTask<?, ?, ?> task);
}
