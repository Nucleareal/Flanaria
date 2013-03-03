package com.Flanaria.MuteQuery.Lunatic;

public enum Enum_MuteCompareType_Long
{
	Number;

	private static final Enum_MuteCompareType_Long[] _values;
	static
	{
		_values = values();
	}
	public int getIndex()
	{
		return ordinal();
	}
	public static Enum_MuteCompareType_Long value(int index)
	{
		return _values[index];
	}
}
