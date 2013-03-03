package com.Flanaria;

import twitter4j.Status;
import android.content.Context;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class CurrentTasks
{
	private CurrentTasks()
	{
	}
	private static CurrentTasks _ins;
	static
	{
		_ins = new CurrentTasks();
	}
	public static CurrentTasks getInstance()
	{
		return _ins;
	}

	private boolean _isFilterable;
	private int _filterType;
	private Retentioner_Authorize _auth;
	private ImageButton _stream;
	private int _inserted;
	private OnClickListener _REST;
	private OnClickListener _STREAM;

	private twitter4j.Status _status;
	public Status getCurrentConversationStatus()
	{
		return _status;
	}
	public void setCurrentConversationStatus(Status status)
	{
		_status = status;
	}

	public void load(Context context)
	{
		_isFilterable = Wrapper_SharedPreference.getInstance().getBoolean(context, "MuteSettings_Category_EnableMute");
		String s = Wrapper_SharedPreference.getInstance().getString(context, "MuteSettings_Category_SelectType");
		Integer value = Integer.valueOf(0);
		try
		{
			value = Integer.valueOf(s);
		}
		catch(Exception e)
		{
			Logger.e(e);
		}
		_filterType = value.intValue();
	}
	public void setMuteEnableSetting(boolean value)
	{
		_isFilterable = value;
	}
	public boolean getIsEnableMute()
	{
		return _isFilterable;
	}
	public void setMuteTypeSetting(int value)
	{
		_filterType = value;
	}
	public int getMuteType()
	{
		return _filterType;
	}

	public void setStreamButton(ImageButton stream)
	{
		_stream = stream;
	}
	public ImageButton getStreamButton()
	{
		return _stream;
	}

	public void setInsertedNumber(int inserted)
	{
		_inserted = inserted;
	}
	public int getInsertedNumber()
	{
		return _inserted;
	}

	public void setCurrentAuthorize(Retentioner_Authorize auth)
	{
		_auth = auth;
	}
	public Retentioner_Authorize getCurrentAuthorize()
	{
		return _auth;
	}

	public void setRESTListener(OnClickListener rest)
	{
		_REST = rest;
	}
	public void setStreamListener(OnClickListener l)
	{
		_STREAM = l;
	}
	public OnClickListener getRESTListener()
	{
		return _REST;
	}
	public OnClickListener getStreamListener()
	{
		return _STREAM;
	}
}
