package com.Flanaria;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;

public class Activity_ScreenNameSearch extends Activity implements IAsyncTaskParent
{
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AsyncTask_SearchScreenNames task = new AsyncTask_SearchScreenNames(this);
        task.execute("");
    }

	public void onTaskCompleted(AsyncTask<?, ?, ?> task)
	{
		AsyncTask_SearchScreenNames task1 = (AsyncTask_SearchScreenNames)task;
		show(task1.getOpenedList(), task1.getMsg());
	}

	private void show(List<String> openedList, String msg)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(msg);

		if(openedList.isEmpty())
		{
			builder.setMessage("空いている2文字ScreenNameはありません。");
		}
		else
		{
			String s1 = "";
			for(String s : openedList)
			{
				s1 += "@"+s+", ";
			}
			builder.setMessage("空いている2文字ScreenNameは"+s1+"です。");
		}

		builder.setPositiveButton(getString(R.string.OK), new OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				finish();
			}
		});

		builder.create().show();
	}
}
