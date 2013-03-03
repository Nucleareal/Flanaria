package com.Flanaria;

import com.Flanaria.MuteQuery.Lunatic.Retentioner_Query;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AsyncTask_OptionMenuListener_Filter extends AsyncTask<String, Integer, Long>
{
	private Context _context;
	private int _itemId;
	private IEditQueryParent _parent;

	public AsyncTask_OptionMenuListener_Filter(Context context, int itemId, IEditQueryParent parent)
	{
		_context = context;
		_itemId = itemId;
		_parent = parent;
	}

	@Override
	protected void onPreExecute()
	{
	}

	@Override
	protected void onPostExecute(Long result)
	{
		switch(_itemId)
		{
			case R.id.Menu_Filter_AddFilter :
			{
				_parent.editQuery(null);
				break;
			}
			case R.id.Menu_Filter_EditQuery :
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(_context);
				View v = ((LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_query_lunatic, null);
				final EditText editText = (EditText)v.findViewById(R.id.EditText_Dialog_Filter);
				editText.setText(Retentioner_Query.getInstance().getRawQuery());
				builder.setView(v);
				builder.setPositiveButton(_context.getString(R.string.Dialog_Filter_Positive), new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						String s = editText.getText().toString();
						Retentioner_Query.getInstance().renewQuery(s, _context);
					}
				});
				builder.setNegativeButton(_context.getString(R.string.Dialog_Filter_Negative), null);
				builder.create().show();
			}
			default :
			{
				break;
			}
		}
	}

	@Override
	protected Long doInBackground(String... arg0)
	{
		return Long.valueOf(0L);
	}
}
