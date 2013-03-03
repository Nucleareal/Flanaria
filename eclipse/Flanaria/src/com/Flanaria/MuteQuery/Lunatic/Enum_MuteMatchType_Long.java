package com.Flanaria.MuteQuery.Lunatic;

public enum Enum_MuteMatchType_Long
{
	Equals("と等価")
	{
		@Override
		public boolean matchQuery(Enum_MuteCompareType_Long comparetype, long source, long pattern)
		{
			return source == pattern;
		}
	},
	Over("超過")
	{
		@Override
		public boolean matchQuery(Enum_MuteCompareType_Long comparetype, long source, long pattern)
		{
			return source > pattern;
		}
	},
	Under("未満")
	{
		@Override
		public boolean matchQuery(Enum_MuteCompareType_Long comparetype, long source, long pattern)
		{
			return source < pattern;
		}
	},
	EOver("以上")
	{
		@Override
		public boolean matchQuery(Enum_MuteCompareType_Long comparetype, long source, long pattern)
		{
			return source >= pattern;
		}
	},
	EUnder("以下")
	{
		@Override
		public boolean matchQuery(Enum_MuteCompareType_Long comparetype, long source, long pattern)
		{
			return source <= pattern;
		}
	},
	NEquals("以外")
	{
		@Override
		public boolean matchQuery(Enum_MuteCompareType_Long comparetype, long source, long pattern)
		{
			return source != pattern;
		}
	},
	Contains("を含む")
	{
		@Override
		public boolean matchQuery(Enum_MuteCompareType_Long comparetype, long source, long pattern)
		{
			return String.valueOf(source).contains(String.valueOf(pattern));
		}
	};

	private String _displayName;
	private Enum_MuteMatchType_Long(String displayName)
	{
		_displayName = displayName;
	}
	public String getDisplayName()
	{
		return _displayName;
	}

	public boolean matchQuery(Enum_MuteCompareType_Long comparetype, long source, long pattern)
	{
		return false;
	}

	private static final Enum_MuteMatchType_Long[] _values;
	static
	{
		_values = values();
	}
	public int getIndex()
	{
		return ordinal();
	}
	public static Enum_MuteMatchType_Long value(int index)
	{
		return _values[index];
	}
}
