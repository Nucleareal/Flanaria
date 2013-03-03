package com.Flanaria.MuteQuery.Lunatic;

import twitter4j.DirectMessage;
import twitter4j.Status;

public enum Enum_MuteSource_Long
{
	UserID("ユーザーID")
	{
		@Override
		public long getSourceLong(Status status)
		{
			return status.getUser().getId();
		}

		@Override
		public long getSourceLong(DirectMessage msg)
		{
			return msg.getSenderId();
		}
	},
	TweetID("ツイートID")
	{
		@Override
		public long getSourceLong(Status status)
		{
			return status.getId();
		}

		@Override
		public long getSourceLong(DirectMessage msg)
		{
			return msg.getId();
		}
	},
	;

	private String _displayName;
	private Enum_MuteSource_Long(String displayName)
	{
		_displayName = displayName;
	}
	public String getDisplayName()
	{
		return _displayName;
	}

	public long getSourceLong(Status status)
	{
		return Long.MIN_VALUE;
	}
	public long getSourceLong(DirectMessage msg)
	{
		return Long.MIN_VALUE;
	}

	private static final Enum_MuteSource_Long[] _values;
	static
	{
		_values = values();
	}
	public int getIndex()
	{
		return ordinal();
	}
	public static Enum_MuteSource_Long value(int index)
	{
		return _values[index];
	}
}
