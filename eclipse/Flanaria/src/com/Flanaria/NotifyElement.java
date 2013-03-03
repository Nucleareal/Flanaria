package com.Flanaria;

import twitter4j.User;

public class NotifyElement
{
	private Enum_NotifyType _type;
	private TwitterElement _elem;
	private String _from;
	private String _to;
	
	//MentionRecived, DirectMessageRecived, RetweetRecived
	public NotifyElement(TwitterElement elem, Enum_NotifyType type)
	{
		_elem = elem;
		_type = type;
		_from = elem.getFromUser().getScreenName();
		_to = elem.getToUser();
	}
	
	//FavoriteRecived, 
	public NotifyElement(User from, User to, TwitterElement elem)
	{
		_elem = elem;
		_from = from.getScreenName();
		_to = to.getScreenName();
		_type = Enum_NotifyType.FavoriteRecived;
	}
	
	public String getFrom()
	{
		return _from;
	}
	
	public String getType()
	{
		switch(_type)
		{
			case StatusRecived:
			{
				return "Tweeted";
			}
			case FavoriteRecived:
			{
				return "Favorited";
			}
			case DirectMessageRecived:
			{
				return "Send Message To";
			}
			case MentionRecived:
			{
				return "Replied To";
			}
			case RetweetRecived:
			{
				return "Retweeted";
			}
		}
		return null;
	}
	
	public String getTo()
	{
		return _to;
	}

	public TwitterElement getElem()
	{
		return _elem;
	}
}
