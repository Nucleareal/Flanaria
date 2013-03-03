package com.Flanaria;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Adapter_TwitterElement extends ArrayAdapter<TwitterElement> implements ILayoutValue
{
	private Context _context;
	private LayoutInflater _inflater;
	private int _omissionCount = -1;
	
	public Adapter_TwitterElement(Context context, int resId, List<TwitterElement> elements)
	{
		super(context, resId, elements);
		_context = context;
		_inflater = ((Activity)_context).getLayoutInflater();
		setNotifyOnChange(true);
	}
	
	//...で省略するかどうか
	public void setOmission(int count)
	{
		_omissionCount = count;
	}

	@Override
	public View getView(int pos, View resView, ViewGroup parent)
	{
	    View view = resView;
	    
	    if (view == null)
	    {  
	    	view = newView();
	    }
	    //表示データセット
	    setViews(view, getItem(pos));
	    
	    return view;
	}
	
	private View newView()
	{
		View view = View.inflate(_context, R.layout.tweet, null); //(View)parent.inflate(_context, R.layout.tweet, null);
		FrameLayout side;
    	ImageView icon;
    	TextView name;
    	TextView text;
    	TextView date;
    	LinearLayout frame;
    	TwitterElementHolder holder;
    	
    	//処理
    	side = (FrameLayout)view.findViewById(R.id.Tweet_ColorFrame);
    	icon = (ImageView)view.findViewById(R.id.Tweet_ImageView);
    	name = (TextView)view.findViewById(R.id.Tweet_Names);
    	text = (TextView)view.findViewById(R.id.Tweet_Text);
    	date = (TextView)view.findViewById(R.id.Tweet_Via);
    	frame = (LinearLayout)view.findViewById(R.id.Tweet_Frame);
    	
    	//保存
    	holder = new TwitterElementHolder();
    	holder._side = side;
    	holder._icon = icon;
    	holder._name = name;
    	holder._text = text;
    	holder._date = date;
    	holder._frame = frame;
        view.setTag(holder); 
        
        return view;
	}
	
	private void setViews(View view, TwitterElement elem)
	{
		TwitterElementHolder holder;
		FrameLayout side;
    	ImageView icon;
    	TextView name;
    	TextView text;
    	TextView date;
    	LinearLayout frame;
    	
    	holder = (TwitterElementHolder)view.getTag();
        side = holder._side;
        icon = holder._icon;
        name = holder._name;
        text = holder._text;
        date = holder._date;
        frame = holder._frame;
        
        String text_t = ReplaceUtils.elemText(elem, text.getText().toString());
        
        if(_omissionCount > 0)
        {
        	text_t = omission(text_t);
        }
		
	    AsyncTask_LoadUserIcon task = new AsyncTask_LoadUserIcon(_context, icon, elem.getUser());
		task.execute(_context.getResources().getString(R.string.app_name));
		name.setText(ReplaceUtils.elemName(elem, name.getText().toString()));
	    text.setText(text_t); if(elem.isRetweet()) text.setText(R.string.Tweet_Default_Retweeted);
	    date.setText(ReplaceUtils.elemDate(elem, date.getText().toString(), _context));
	    side.setBackgroundColor(_context.getResources().getColor(Utils_TwitterElement.getColor(elem)));
	    TwitterElement[] nelem;
	    if((nelem = Utils_TwitterElement.getExtraElements(elem)) != null)
	    {
	    	if(frame.getChildCount() > 0)
	    	{
	    		return;
	    	}
	    	for(TwitterElement elem1 : nelem)
	    	{
	    		LinearLayout inl = new LinearLayout(_context);
	    		View view1 = newView();
	    		setViews(view1, elem1);
				inl.setPadding(0, 4, 0, 4);
				//inl.addView(view1, new AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
				frame.addView(inl, new AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
	    	}
	    }
	    else
	    {
	    	frame.removeAllViews();
	    }
	}

	private String omission(String text_t)
	{
		if(text_t.length() <= _omissionCount)
		{
			return text_t;
		}
		text_t = text_t.substring(0, _omissionCount-1-3);
		text_t += "...";
		return text_t;
	}
}
