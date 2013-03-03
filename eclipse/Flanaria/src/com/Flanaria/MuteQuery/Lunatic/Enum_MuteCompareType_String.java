package com.Flanaria.MuteQuery.Lunatic;

public enum Enum_MuteCompareType_String
{
	Text("語句"),
	Regex("正規表現");

	private String _displayName;
	private Enum_MuteCompareType_String(String displayName)
	{
		_displayName = displayName;
	}
	public String getDisplayName()
	{
		return _displayName;
	}

	private static final Enum_MuteCompareType_String[] _values;
	static
	{
		_values = values();
	}
	public int getIndex()
	{
		return ordinal();
	}
	public static Enum_MuteCompareType_String value(int index)
	{
		return _values[index];
	}
}
