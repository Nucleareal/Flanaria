package com.Flanaria.MuteQuery.Lunatic;

import twitter4j.DirectMessage;
import twitter4j.Status;

public interface IFlanariaMuteQuery<T>
{
	boolean getIsMatch(Status status);

	Object[] getSaveArray();

	String getShowText();

	void renewQuery(Object o1, Object o2, T o3, Object o4, boolean o5);

	IFlanariaMuteQuery<T> getClone();

	Enum_QueryType getQueryType();

	int getSourceTypeOrdinal();

	int getCompareTypeOrdinal();

	int getMatchTypeOrdinal();

	String getCompareBase();

	boolean getContainRetweet();

	void setNumber(int number);

	boolean getIsMatch(DirectMessage msg);
}
