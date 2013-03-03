package com.Flanaria;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
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

import com.Flanaria.R;

public class Adapter_Tweet extends ArrayAdapter<Status> implements ILayoutValue
{
	private Activity _parent;
	private List<Status> _list;
	private List<View> _viewlist;
	private View _current;

	public Adapter_Tweet(Context context, int textViewResourceId, ArrayList<Status> items)
	{
		super(context, textViewResourceId, items);
		_parent = (Activity)context;
		_list = items;
		_viewlist = new ArrayList<View>();
	}

	public List<Status> getList()
	{
		return _list;
	}

	public View getCurrentView()
	{
		return _current;
	}

	@Override
	public void insert(Status status, int index)
	{
		_list.add(index, status);

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
			Status status = getItem(position);
			if(status != null)
			{
				registerView(view, status, 0);
			}
		}
		return view;
	}

	public void registerView(View parent, Status status, int depth)
	{
		TextView Name = (TextView)parent.findViewById(R.id.Tweet_Names);
		TextView Text = (TextView)parent.findViewById(R.id.Tweet_Text);
		TextView Via = (TextView)parent.findViewById(R.id.Tweet_Via);
		Name.setText(R.string.Tweet_Default_Names);
		Text.setText(R.string.Tweet_Default_Text);
		Via .setText(R.string.Tweet_Default_Via);

		Name.setText(ReplaceUtils.replaceTweetUserNames(Name.getText().toString(), status));
		Text.setText(ReplaceUtils.replaceTweetText(Text.getText().toString(), status));
		Via .setText(ReplaceUtils.replaceTweetVias(Via.getText().toString(), status));

		if(status.isRetweet())
		{
			Text.setText(R.string.Tweet_Default_Retweeted);
		}
		FrameLayout cl = (FrameLayout)parent.findViewById(R.id.Tweet_ColorFrame);
		StatusUtils.setFrameColor(_parent, cl, status);

		ImageView Image = (ImageView)parent.findViewById(R.id.Tweet_ImageView);
		AsyncTask_LoadUserIcon task = new AsyncTask_LoadUserIcon(_parent, Image, status.getUser());
		task.execute(parent.getResources().getString(R.string.app_name));

		Status[] ex_status;
		if(depth <= 0 && (ex_status = StatusUtils.getExtraStatus(status)) != null && ex_status.length > 0)
		{
			LinearLayout layout = (LinearLayout)parent.findViewById(R.id.Tweet_Frame);
			if(layout.getChildCount() > 0)
			{
				return;
			}
			for(Status _status : ex_status)
			{
				LayoutInflater inflater = (LayoutInflater)_parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.tweet, null);
				registerView(view, _status, depth+1);
				LinearLayout inl = new LinearLayout(_parent);
				inl.setPadding(0, 4, 0, 4);
				inl.addView(view, new LinearLayout.LayoutParams(FILL, WRAP));
				layout.addView(inl, new LinearLayout.LayoutParams(FILL, WRAP));
			}
		}
		else
		{
			LinearLayout layout = (LinearLayout)parent.findViewById(R.id.Tweet_Frame);
			layout.removeAllViews();
		}
	}
}
