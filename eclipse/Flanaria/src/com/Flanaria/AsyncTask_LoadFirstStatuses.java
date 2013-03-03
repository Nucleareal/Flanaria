package com.Flanaria;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class AsyncTask_LoadFirstStatuses extends AsyncTask<String, Integer, Long>
{
	private Context _context;
	private boolean _isFinished;
	IAsyncTaskParent _parent;
	private ImageButton _stream;
	private CheckBox _box;

	public AsyncTask_LoadFirstStatuses(Context context, IAsyncTaskParent parent, ImageButton stream)
	{
		_context = context;
		_parent = parent;
		_stream = stream;
	}

	@Override
	protected void onPreExecute()
	{
		_isFinished = false;
		/*_dialog = new ProgressDialog(_context);
		_dialog.setTitle(R.string.Timeline_LoadStatus_Title);
		_dialog.setMessage(_context.getResources().getText(R.string.Timeline_LoadStatus_Message));
		_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		_dialog.setCancelable(false);
		_dialog.show();*/

		if(Wrapper_SharedPreference.getInstance().getBoolean(_context, _context.getString(R.string.StatusLoadWay_Default_Setting_Key), false))
		{
			if(Wrapper_SharedPreference.getInstance().getBoolean(_context, _context.getString(R.string.StatusLoadWay_Default_Setting_Value), false))
			{
				ReadyStream();
			}
			return;
		}

		AlertDialog.Builder builder = DialogUtils.createDialogBuilder(_context,
				_context.getResources().getString(R.string.Timeline_LoadStatus_Select_Title),
				null);
		LinearLayout layout = (LinearLayout)((Activity)_context).getLayoutInflater().inflate(R.layout.dialog_status_select, null);
		_box = (CheckBox)layout.findViewById(R.id.CheckBox_StatusSelect_SetDefault);
		builder.setView(layout);
		//REST API
		builder.setPositiveButton(_context.getResources().getString(R.string.Timeline_LoadStatus_Select_Positive),
				new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				saveChecked(false);
				ReadyREST();
			}
		});
		//UserStreams
		builder.setNegativeButton(_context.getResources().getString(R.string.Timeline_LoadStatus_Select_Negative),
				new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				saveChecked(true);
				ReadyStream();
			}
		});
		builder.setCancelable(false);
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void saveChecked(boolean value)
	{
		boolean isChecked = _box.isChecked();
		Wrapper_SharedPreference.getInstance().putBoolean(_context, _context.getString(R.string.StatusLoadWay_Default_Setting_Key), isChecked);
		Wrapper_SharedPreference.getInstance().putBoolean(_context, _context.getString(R.string.StatusLoadWay_Default_Setting_Value), value);
	}

	private void ReadyREST()
	{
		_stream = CurrentTasks.getInstance().getStreamButton();
		_stream.setOnClickListener(CurrentTasks.getInstance().getRESTListener());
	}

	private void ReadyStream()
	{
		Retentioner_Users.getInstance().connectAllUserStreams();
		_stream = CurrentTasks.getInstance().getStreamButton();
		_stream.setImageDrawable(_context.getResources().getDrawable(R.drawable.av_pause));
		_stream.setOnClickListener(CurrentTasks.getInstance().getStreamListener());
	}

	@Override
	protected void onPostExecute(Long result)
	{
		Adapter_Tweet adapter = null;
		if(_context instanceof ListActivity)
		{
			ListActivity activity = (ListActivity)_context;
			adapter = (Adapter_Tweet)activity.getListAdapter();
		}
		//1ステータスずつ入れていく
		for(twitter4j.Status status : _list)
		{
			StatusUtils.insertStatus(_context, adapter, status);
		}
		//_dialog.dismiss();
		_parent.onTaskCompleted(this);
	}

	private List<twitter4j.Status> _list;

	@Override
	protected Long doInBackground(String... arg0)
	{
		_list = Retentioner_Users.getInstance().getAllUsersStatus();
		return Long.valueOf(0);
	}

	public boolean getIsFinished()
	{
		return _isFinished;
	}
}
