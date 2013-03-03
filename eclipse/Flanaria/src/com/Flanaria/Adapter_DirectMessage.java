package com.Flanaria;

import java.util.ArrayList;
import java.util.List;

import twitter4j.DirectMessage;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Adapter_DirectMessage extends ArrayAdapter<DirectMessage> implements ILayoutValue
{
	private Activity _parent;
	private List<DirectMessage> _list;
	private List<View> _viewlist;
	private View _current;

	public Adapter_DirectMessage(Context context, int textViewResourceId, ArrayList<DirectMessage> arrayList)
	{
		super(context, textViewResourceId, arrayList);
		_parent = (Activity)context;
		_list = arrayList;
		_viewlist = new ArrayList<View>();
	}

	public List<DirectMessage> getList()
	{
		return _list;
	}

	public View getCurrentView()
	{
		return _current;
	}

	@Override
	public void insert(DirectMessage msg, int index)
	{
		_list.add(index, msg);

		View view = getView(index, null, null);

		_viewlist.add(index, view);

		_current = view;
	}

/*	@Override
	public Status getItem(int position)
	{
		if(position >= _items.size())
		{
			return null;
		}
		return super.getItem(position);
	}*/

	@Override
	public View getView(int position, View view, ViewGroup parent)
	{
		if(parent != null && position < _viewlist.size())
		{
			return _viewlist.get(position);
		}
		LayoutInflater inflater = (LayoutInflater)_parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(view == null)
		{
			view = inflater.inflate(R.layout.tweet, null);
			DirectMessage msg = getItem(position);
			if(msg != null)
			{
				registerView(view, msg, 0);
			}
		}
		return view;
	}

	public void registerView(View parent, DirectMessage msg, int depth)
	{
		TextView Name = (TextView)parent.findViewById(R.id.Tweet_Names);
		TextView Text = (TextView)parent.findViewById(R.id.Tweet_Text);
		TextView Via = (TextView)parent.findViewById(R.id.Tweet_Via);
		Name.setText(R.string.Tweet_Default_Names);
		Text.setText(R.string.Tweet_Default_Text);
		Via .setText(R.string.Tweet_Default_Via);

		Name.setText(ReplaceUtils.replaceMsgUserNames(Name.getText().toString(), msg));
		Text.setText(ReplaceUtils.replaceMsgText(Text.getText().toString(), msg));
		Via .setText(ReplaceUtils.replaceMsgVias(Via.getText().toString(), msg));

		FrameLayout cl = (FrameLayout)parent.findViewById(R.id.Tweet_ColorFrame);
		DirectMessageUtils.setFrameColor(_parent, cl, msg);

		ImageView Image = (ImageView)parent.findViewById(R.id.Tweet_ImageView);
		AsyncTask_LoadUserIcon task = new AsyncTask_LoadUserIcon(_parent, Image, msg.getSender());
		task.execute(parent.getResources().getString(R.string.app_name));

		LinearLayout layout = (LinearLayout)parent.findViewById(R.id.Tweet_Frame);
		layout.removeAllViews();
	}
}
