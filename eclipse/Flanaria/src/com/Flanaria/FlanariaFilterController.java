package com.Flanaria;

import twitter4j.DirectMessage;
import twitter4j.Status;

import com.Flanaria.MuteQuery.Lunatic.Retentioner_Query;

public class FlanariaFilterController
{
	private FlanariaFilterController()
	{
	}
	private static FlanariaFilterController _ins;
	static
	{
		_ins = new FlanariaFilterController();
	}
	public static FlanariaFilterController getInstance()
	{
		return _ins;
	}

	public boolean isShouldFilter(Status status)
	{
		if(CurrentTasks.getInstance().getIsEnableMute())
		{
			switch(CurrentTasks.getInstance().getMuteType())
			{
				case 0 :
				{
					return com.Flanaria.MuteQuery.Easy.FlanariaMuteQueryParser.isShouldFilter(status);
				}
				case 1 :
				{
					return com.Flanaria.MuteQuery.Lunatic.FlanariaMuteQueryParser.isShouldFilter(status, Retentioner_Query.getInstance().getRawQuery());
				}
			}
		}
		return false;
	}

	public boolean isShouldFilter(DirectMessage msg)
	{
		if(CurrentTasks.getInstance().getIsEnableMute())
		{
			switch(CurrentTasks.getInstance().getMuteType())
			{
				case 0 :
				{
					return com.Flanaria.MuteQuery.Easy.FlanariaMuteQueryParser.isShouldFilter(msg);
				}
				case 1 :
				{
					return com.Flanaria.MuteQuery.Lunatic.FlanariaMuteQueryParser.isShouldFilter(msg, Retentioner_Query.getInstance().getRawQuery());
				}
			}
		}
		return false;
	}

	public boolean isShouldFilter(TwitterElement elem)
	{
		switch(elem.getType())
		{
			case Status:
			{
				return isShouldFilter(elem.getStatus());
			}
			case DirectMessage:
			{
				return isShouldFilter(elem.getDirectMessage());
			}
		}
		return false;
	}
}
