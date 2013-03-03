package com.Flanaria;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.Flanaria.MuteQuery.Lunatic.IFlanariaMuteQuery;

public class MuteFilterAdapter extends ArrayAdapter<IFlanariaMuteQuery<?>>
{
	private Context _context;

	public MuteFilterAdapter(Context context, int textViewResourceId, List<IFlanariaMuteQuery<?>> list)
	{
		super(context, textViewResourceId, list);
		_context = context;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent)
	{
		if(view == null)
		{
			view = LayoutInflater.from(_context).inflate(R.layout.query_show, null);
		}
		IFlanariaMuteQuery<?> query = getItem(position);
		if(query != null)
		{
			TextView textView = (TextView)view.findViewById(R.id.TextView_Query_ShowText);
			textView.setText(query.getShowText());
		}
		return view;
	}
}
