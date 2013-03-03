package com.Flanaria.MuteQuery.Lunatic;

import twitter4j.DirectMessage;
import twitter4j.Status;

public class FlanariaMuteQuery_Long implements IFlanariaMuteQuery<Long>
{
	private Enum_MuteSource_Long _replaceWay;
	private Enum_MuteMatchType_Long _matchType;
	private Enum_MuteCompareType_Long _compareType;
	private long _compareid;
	private boolean _containRetweet;
	private int _number;

	public FlanariaMuteQuery_Long(
			Enum_MuteSource_Long muteSource,
			Enum_MuteCompareType_Long compareType,
			long compareString,
			Enum_MuteMatchType_Long matchType,
			boolean containRetweet)
	{
		_replaceWay = muteSource;
		_compareType = compareType;
		_compareid = compareString;
		_matchType = matchType;
		_containRetweet = containRetweet;
	}

	public void renewQuery(Object o1, Object o2, Long o3, Object o4, boolean o5)
	{
		_replaceWay = (Enum_MuteSource_Long)o1;
		_compareType = (Enum_MuteCompareType_Long)o2;
		_compareid = o3.longValue();
		_matchType = (Enum_MuteMatchType_Long)o4;
		_containRetweet = o5;
	}

	public boolean getIsMatch(Status status)
	{
		return _matchType.matchQuery(_compareType, _replaceWay.getSourceLong(status), _compareid) ||
				(_containRetweet && status.isRetweet() && _matchType.matchQuery(_compareType, _replaceWay.getSourceLong(status.getRetweetedStatus()), _compareid));
	}

	public boolean getIsMatch(DirectMessage msg)
	{
		return _matchType.matchQuery(_compareType, _replaceWay.getSourceLong(msg), _compareid);
	}

	public Object[] getSaveArray()
	{
		return new Object[]
		{
			Integer.valueOf(_compareType.ordinal()),
			Integer.valueOf(_matchType.ordinal()),
			Integer.valueOf(_replaceWay.ordinal()),
			Long.valueOf(_compareid),
			Boolean.valueOf(_containRetweet),
		};
	}

	public String getShowText()
	{
		return "["+_number+"]:"+_replaceWay.getDisplayName()+"が"+_compareid+_matchType.getDisplayName();
	}

	public IFlanariaMuteQuery<Long> getClone()
	{
		return new FlanariaMuteQuery_Long(_replaceWay, _compareType, _compareid, _matchType, _containRetweet);
	}

	public Enum_QueryType getQueryType()
	{
		return Enum_QueryType.Long;
	}

	public int getSourceTypeOrdinal()
	{
		return _replaceWay.ordinal();
	}

	public int getCompareTypeOrdinal()
	{
		return _compareType.ordinal();
	}

	public int getMatchTypeOrdinal()
	{
		return _matchType.ordinal();
	}

	public String getCompareBase()
	{
		return String.valueOf(_compareid);
	}

	public boolean getContainRetweet()
	{
		return _containRetweet;
	}

	public void setNumber(int number)
	{
		_number = number;
	}
}
