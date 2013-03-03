package com.Flanaria;

import java.util.Date;

import twitter4j.DirectMessage;
import android.app.Activity;
import android.widget.FrameLayout;

public class DirectMessageUtils
{

	public static void insertMessage(Activity act, Adapter_DirectMessage adapter, DirectMessage msg)
	{
		if(FlanariaFilterController.getInstance().isShouldFilter(msg))
		{
			return;
		}

		int i;
		DirectMessage _status = null;
		Date createdAt = msg.getCreatedAt();
		Date _createdAt = null;
		int firstEqualsStatus = -1;
		for(i = 0; i < adapter.getCount(); i++)
		{
			_status = adapter.getItem(i);
			if(_status == null)
			{
				break;
			}
			_createdAt = _status.getCreatedAt();
			int var = _createdAt.compareTo(createdAt);
			if(var == 0)
			{
				if(firstEqualsStatus < 0)
				{
					firstEqualsStatus = i;
				}
				if(equalsStatusesNeighbor(_status, msg))
				{
					return;
				}
			}
			if(var < 0)
			{
				break;
			}
		}
		if(firstEqualsStatus >= 0)
		{
			i = firstEqualsStatus;
		}
		adapter.insert(msg, i);
		adapter.notifyDataSetChanged();
		CurrentTasks.getInstance().setInsertedNumber(i);
	}

	public static boolean equalsStatusesNeighbor(DirectMessage msg0, DirectMessage msg1)
	{
		if(msg0 == null)
		{
			return msg1 == null;
		}
		return msg1.getSender().getId() == msg0.getSender().getId() &&
				msg1.getText().equals(msg0.getText());
	}

	public static void setFrameColor(Activity context, FrameLayout layout, DirectMessage msg)
	{
		layout.setBackgroundColor(context.getResources().getColor(R.color.TweetFrame_DirectMessage));
	}

}
