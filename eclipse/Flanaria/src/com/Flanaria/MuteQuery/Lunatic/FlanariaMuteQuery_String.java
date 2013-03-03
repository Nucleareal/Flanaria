package com.Flanaria.MuteQuery.Lunatic;

import twitter4j.DirectMessage;
import twitter4j.Status;

public class FlanariaMuteQuery_String implements IFlanariaMuteQuery<String>
{
	private Enum_MuteSource_String _replaceWay;
	private Enum_MuteMatchType_String _matchType;
	private Enum_MuteCompareType_String _compareType;
	private String _compareString;
	private boolean _containRetweet;
	private int _number;

	public FlanariaMuteQuery_String(
			Enum_MuteSource_String muteSource,
			Enum_MuteCompareType_String compareType,
			String compareString,
			Enum_MuteMatchType_String matchType,
			boolean containRetweet)
	{
		_replaceWay = muteSource;
		_compareType = compareType;
		_compareString = compareString;
		_matchType = matchType;
		_containRetweet = containRetweet;
	}

	public void renewQuery(Object o1, Object o2, String o3, Object o4, boolean o5)
	{
		_replaceWay = (Enum_MuteSource_String)o1;
		_compareType = (Enum_MuteCompareType_String)o2;
		_compareString = o3;
		_matchType = (Enum_MuteMatchType_String)o4;
		_containRetweet = o5;
	}

	public boolean getIsMatch(Status status)
	{
		return _matchType.matchQuery(_compareType, _replaceWay.getSourceString(status), _compareString) ||
				(_containRetweet && status.isRetweet() && _matchType.matchQuery(_compareType, _replaceWay.getSourceString(status.getRetweetedStatus()), _compareString));
	}

	public boolean getIsMatch(DirectMessage msg)
	{
		return _matchType.matchQuery(_compareType, _replaceWay.getSourceString(msg), _compareString);
	}

	public Object[] getSaveArray()
	{
		return new Object[]
		{
			Integer.valueOf(_compareType.ordinal()),
			Integer.valueOf(_matchType.ordinal()),
			Integer.valueOf(_replaceWay.ordinal()),
			String.valueOf(_compareString),
			Boolean.valueOf(_containRetweet),
		};
	}

	public String getShowText()
	{
		return "{"+_number+"}:"+_replaceWay.getDisplayName()+"が"+_compareType.getDisplayName()+_compareString+_matchType.getDisplayName();
	}

	public IFlanariaMuteQuery<String> getClone()
	{
		return new FlanariaMuteQuery_String(_replaceWay, _compareType, _compareString, _matchType, _containRetweet);
	}

	public Enum_QueryType getQueryType()
	{
		return Enum_QueryType.String;
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
		return _compareString;
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
