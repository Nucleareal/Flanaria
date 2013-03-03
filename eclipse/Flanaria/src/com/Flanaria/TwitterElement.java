package com.Flanaria;

import java.util.Date;

import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.User;
import android.content.Context;

public class TwitterElement
{
	private Status _status;
	private DirectMessage _dmsg;
	private Enum_ElementType _type;
	
	public TwitterElement(Status status)
	{
		_status = status;
		_type = Enum_ElementType.Status;
	}
	
	public TwitterElement(DirectMessage msg)
	{
		_dmsg = msg;
		_type = Enum_ElementType.DirectMessage;
	}
	
	public Enum_ElementType getType()
	{
		return _type;
	}
	
	public Date getCreatedAt()
	{
		switch(_type)
		{
			case Status:
			{
				return _status.getCreatedAt();
			}
			case DirectMessage:
			{
				return _dmsg.getCreatedAt();
			}
		}
		return null;
	}
	
	public String getText()
	{
		switch(_type)
		{
			case Status:
			{
				return _status.getText();
			}
			case DirectMessage:
			{
				return _dmsg.getText();
			}
		}
		return null;
	}
	
	public User getUser()
	{
		switch(_type)
		{
			case Status:
			{
				return _status.getUser();
			}
			case DirectMessage:
			{
				return _dmsg.getSender();
			}
		}
		return null;
	}
	
	public String getSource(Context context)
	{
		switch(_type)
		{
			case Status:
			{
				return _status.getSource();
			}
			case DirectMessage:
			{
				return context.getString(R.string.Source_Message);
			}
		}
		return null;
	}
	
	public boolean isRetweet()
	{
		switch(_type)
		{
			case Status:
			{
				return _status.isRetweet();
			}
			case DirectMessage:
			{
				return false;
			}
		}
		return false;
	}
	
	public Status getStatus()
	{
		return _status;
	}
	
	public DirectMessage getDirectMessage()
	{
		return _dmsg;
	}

	public User getFromUser()
	{
		switch(_type)
		{
			case Status:
			{
				return _status.getUser();
			}
			case DirectMessage:
			{
				return _dmsg.getSender();
			}
		}
		return null;
	}

	public String getToUser()
	{
		switch(_type)
		{
			case Status:
			{	
				String res; 
				if((res = StatusUtils.getMentionRecivedScreenName(_status)) != null)
				{
					return res;
				}
				if(_status.isRetweet())
				{
					return _status.getRetweetedStatus().getUser().getScreenName();
				}
				return _status.getUser().getScreenName();
			}
			case DirectMessage:
			{
				return _dmsg.getRecipient().getScreenName();
			}
		}
		return null;
	}
}
