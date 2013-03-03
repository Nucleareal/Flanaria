package com.Flanaria;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class NotifyDispatcher
{
	public void dispatch(Context context, NotifyElement elem)
	{
		LayoutInflater inf = LayoutInflater.from(context);
		LinearLayout layout = (LinearLayout)inf.inflate(R.layout.notify, null);
		
		TextView view = (TextView)layout.findViewById(R.id.Notify_Text);
		ListView list = (ListView)layout.findViewById(R.id.Notify_Tweet);
		
		Adapter_TwitterElement adp = new Adapter_TwitterElement(context, R.layout.tweet, new LinkedList<TwitterElement>());
		list.setAdapter(adp);
		
		String text = elem.getFrom() +" "+ elem.getType() +" "+ elem.getTo();
		view.setText(text);
		Utils_TwitterElement.insert(adp, elem.getElem());
		
		ToastUtils.showViewToastOnActivity((Activity)context, layout, true);
	}
	
	//
	
	private static final NotifyDispatcher _ins = new NotifyDispatcher();
	public static NotifyDispatcher getInstance()
	{
		return _ins;
	}
	private NotifyDispatcher()
	{
	}
}
