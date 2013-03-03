package com.Flanaria;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import com.Flanaria.MuteQuery.Lunatic.Enum_MuteCompareType_Long;
import com.Flanaria.MuteQuery.Lunatic.Enum_MuteCompareType_String;
import com.Flanaria.MuteQuery.Lunatic.Enum_MuteMatchType_Long;
import com.Flanaria.MuteQuery.Lunatic.Enum_MuteMatchType_String;
import com.Flanaria.MuteQuery.Lunatic.Enum_MuteSource_Long;
import com.Flanaria.MuteQuery.Lunatic.Enum_MuteSource_String;
import com.Flanaria.MuteQuery.Lunatic.FlanariaMuteQuery_Long;
import com.Flanaria.MuteQuery.Lunatic.FlanariaMuteQuery_String;
import com.Flanaria.MuteQuery.Lunatic.IFlanariaMuteQuery;
import com.Flanaria.MuteQuery.Lunatic.Retentioner_Query;

public class Activity_MuteFilter extends ListActivity implements OnItemLongClickListener, IEditQueryParent
{
	private MuteFilterAdapter _adapter;
	private Context _context = this;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setViews();
	}

	private void setViews()
	{
		List<IFlanariaMuteQuery<?>> list = com.Flanaria.MuteQuery.Lunatic.Retentioner_Query.getInstance().getAllQueriesList();
		_adapter = new MuteFilterAdapter(this, R.layout.query_show, list);
		LinearLayout emptyView = (LinearLayout)getLayoutInflater().inflate(R.layout.query_empty, null);
		getListView().setEmptyView(emptyView);
		setListAdapter(_adapter);
		setContentView(R.layout.query_empty);
		getListView().setOnItemLongClickListener(this);
	}

	public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id)
	{
		final IFlanariaMuteQuery<?> query = _adapter.getItem(pos);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setItems(new String[]
				{
					getString(R.string.Query_Menu_Action_Edit),
					getString(R.string.Query_Menu_Action_Copy),
					getString(R.string.Query_Menu_Action_Delete),
				}, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				switch(which)
				{
					case 0 :
					{
						editQuery(query);
						break;
					}
					case 1 :
					{
						IFlanariaMuteQuery<?> query2 = query.getClone();
						_adapter.add(query2);
						_adapter.notifyDataSetChanged();
						Retentioner_Query.getInstance().addQuery(query2);
						Retentioner_Query.getInstance().save(_context);
						break;
					}
					case 2 :
					{
						_adapter.remove(query);
						_adapter.notifyDataSetChanged();
						Retentioner_Query.getInstance().removeQuery(query);
						Retentioner_Query.getInstance().save(_context);
						break;
					}
				}
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
		return true;
	}

	private boolean _isEditing = true;

	public void editQuery(final IFlanariaMuteQuery<?> query)
	{
		if(query == null)
		{
			_isEditing = false;
			showSelectDialog();
			return;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.Query_Edit_Title));
		builder.setCancelable(false);
		LinearLayout layout = null;
		Spinner Source;
		Spinner CompareType = null;
		Spinner MatchType;
		EditText Comparor;
		CheckBox ContainRetweet;
		switch(query.getQueryType())
		{
			default :
			case String :
			{
				layout = (LinearLayout)getLayoutInflater().inflate(R.layout.query_makejudge_string, null);
				Source = (Spinner)layout.findViewById(R.id.Spinner_Filter_Make_Source_String);
				CompareType = (Spinner)layout.findViewById(R.id.Spinner_Filter_Make_ContentType_String);
				MatchType = (Spinner)layout.findViewById(R.id.Spinner_Filter_Make_MatchType_String);
				Comparor = (EditText)layout.findViewById(R.id.EditText_Filter_Make_Text_String);
				ContainRetweet = (CheckBox)layout.findViewById(R.id.CheckBox_Filter_Make_ContainRetweet);
				break;
			}
			case Long :
			{
				layout = (LinearLayout)getLayoutInflater().inflate(R.layout.query_makejudge_long, null);
				Source = (Spinner)layout.findViewById(R.id.Spinner_Filter_Make_Source_Long);
				MatchType = (Spinner)layout.findViewById(R.id.Spinner_Filter_Make_MatchType_Long);
				Comparor = (EditText)layout.findViewById(R.id.EditText_Filter_Make_Text_Long);
				ContainRetweet = (CheckBox)layout.findViewById(R.id.CheckBox_Filter_Make_ContainRetweet);
				break;
			}
		}
		Source.setSelection(query.getSourceTypeOrdinal());
		if(CompareType != null)
		{
			CompareType.setSelection(query.getCompareTypeOrdinal());
		}
		MatchType.setSelection(query.getMatchTypeOrdinal());
		Comparor.setText(query.getCompareBase());
		ContainRetweet.setChecked(query.getContainRetweet());
		builder.setView(layout);
		final LinearLayout lis = layout;
		builder.setPositiveButton(getString(R.string.Dialog_Filter_Positive), new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				Spinner Source;
				Spinner CompareType;
				Spinner MatchType;
				EditText Comparor;
				CheckBox ContainRetweet;
				//Spinnerから情報をgetします
				switch(query.getQueryType())
				{
					default :
					case String :
					{
						Source = (Spinner)lis.findViewById(R.id.Spinner_Filter_Make_Source_String);
						CompareType = (Spinner)lis.findViewById(R.id.Spinner_Filter_Make_ContentType_String);
						MatchType = (Spinner)lis.findViewById(R.id.Spinner_Filter_Make_MatchType_String);
						Comparor = (EditText)lis.findViewById(R.id.EditText_Filter_Make_Text_String);
						ContainRetweet = (CheckBox)lis.findViewById(R.id.CheckBox_Filter_Make_ContainRetweet);
						((IFlanariaMuteQuery<String>)query).renewQuery(
							Enum_MuteSource_String.value(Source.getSelectedItemPosition()),
							Enum_MuteCompareType_String.value(CompareType.getSelectedItemPosition()),
							Comparor.getText().toString(),
							Enum_MuteMatchType_String.value(MatchType.getSelectedItemPosition()),
							ContainRetweet.isChecked());
						break;
					}
					case Long :
					{
						Source = (Spinner)lis.findViewById(R.id.Spinner_Filter_Make_Source_Long);
						MatchType = (Spinner)lis.findViewById(R.id.Spinner_Filter_Make_MatchType_Long);
						Comparor = (EditText)lis.findViewById(R.id.EditText_Filter_Make_Text_Long);
						ContainRetweet = (CheckBox)lis.findViewById(R.id.CheckBox_Filter_Make_ContainRetweet);
						((IFlanariaMuteQuery<Long>)query).renewQuery(
							Enum_MuteSource_Long.value(Source.getSelectedItemPosition()),
							Enum_MuteCompareType_Long.Number,
							Long.valueOf(Comparor.getText().toString()),
							Enum_MuteMatchType_Long.value(MatchType.getSelectedItemPosition()),
							ContainRetweet.isChecked());
						break;
					}
				}
				Retentioner_Query.getInstance().addQuery(query);
				Retentioner_Query.getInstance().save(_context);
				if(!_isEditing)
				{
					_adapter.add(query);
				}
				_isEditing = true;
				_adapter.notifyDataSetChanged();
			}
		});
		builder.setNegativeButton(getString(R.string.Dialog_Filter_Negative), null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void showSelectDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setItems(new String[]
				{
					getString(R.string.Query_SelectType_String),
					getString(R.string.Query_SelectType_Long),
				},
				new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				IFlanariaMuteQuery<?> query = null;
				switch(which)
				{
					default :
					case 0 :
					{
						query = new FlanariaMuteQuery_String(
								Enum_MuteSource_String.UserScreenName,
								Enum_MuteCompareType_String.Text,
								"",
								Enum_MuteMatchType_String.Contains,
								true);
						break;
					}
					case 1 :
					{
						query = new FlanariaMuteQuery_Long(
								Enum_MuteSource_Long.UserID,
								Enum_MuteCompareType_Long.Number,
								0L,
								Enum_MuteMatchType_Long.Equals,
								true);
						break;
					}
				}
				editQuery(query);
			}
		});
		builder.setCancelable(false);
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.filter, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		AsyncTask_OptionMenuListener_Filter task = new AsyncTask_OptionMenuListener_Filter(this, item.getItemId(), this);
		task.execute(getString(R.string.app_name));
		return true;
	}
}
