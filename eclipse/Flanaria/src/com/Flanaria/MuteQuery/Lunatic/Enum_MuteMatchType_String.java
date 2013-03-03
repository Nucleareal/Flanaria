package com.Flanaria.MuteQuery.Lunatic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Enum_MuteMatchType_String
{
	Contains("を含む")
	{
		@Override
		public boolean matchQuery(Enum_MuteCompareType_String comparetype, String source, String spattern)
		{
			switch(comparetype)
			{
				case Text :
				{
					return source.contains(spattern);
				}
				case Regex :
				{
					Pattern pattern = Pattern.compile(spattern);
					Matcher matcher = pattern.matcher(source);
					return matcher.find();
				}
			}
			return false;
		}
	},
	Equals("と完全一致")
	{
		@Override
		public boolean matchQuery(Enum_MuteCompareType_String comparetype, String source, String spattern)
		{
			switch(comparetype)
			{
				case Text :
				{
					return source.equals(spattern);
				}
				case Regex :
				{
					Pattern pattern = Pattern.compile("^"+spattern+"$");
					Matcher matcher = pattern.matcher(source);
					return matcher.find();
				}
			}
			return false;
		}
	};

	private String _displayName;
	private Enum_MuteMatchType_String(String displayName)
	{
		_displayName = displayName;
	}
	public String getDisplayName()
	{
		return _displayName;
	}

	public boolean matchQuery(Enum_MuteCompareType_String comparetype, String source, String pattern)
	{
		return false;
	}

	private static final Enum_MuteMatchType_String[] _values;
	static
	{
		_values = values();
	}
	public int getIndex()
	{
		return ordinal();
	}
	public static Enum_MuteMatchType_String value(int index)
	{
		return _values[index];
	}
}
