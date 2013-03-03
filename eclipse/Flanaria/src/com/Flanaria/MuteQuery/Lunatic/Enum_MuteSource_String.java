package com.Flanaria.MuteQuery.Lunatic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.DirectMessage;
import twitter4j.Status;
import android.text.Html;

public enum Enum_MuteSource_String
{
	UserScreenName("ユーザースクリーンネーム")
	{
		@Override
		public String getSourceString(Status status)
		{
			return status.getUser().getScreenName();
		}

		@Override
		public String getSourceString(DirectMessage msg)
		{
			return msg.getSender().getScreenName();
		}
	},
	UserName("ユーザーネーム")
	{
		@Override
		public String getSourceString(Status status)
		{
			return status.getUser().getName();
		}

		@Override
		public String getSourceString(DirectMessage msg)
		{
			return msg.getSender().getName();
		}
	},
	ClientName("クライアント名")
	{
		@Override
		public String getSourceString(Status status)
		{
			return Html.fromHtml(status.getSource()).toString();
		}

		@Override
		public String getSourceString(DirectMessage msg)
		{
			return "";
		}
	},
	ClientURL("クライアントURL")
	{
		@Override
		public String getSourceString(Status status)
		{
			Pattern pattern = Pattern.compile("\".*\"");
			Matcher matcher = pattern.matcher(status.getSource());
			return matcher.group().replace("\"", "");
		}

		@Override
		public String getSourceString(DirectMessage msg)
		{
			return "";
		}
	},
	TweetText("本文")
	{
		@Override
		public String getSourceString(Status status)
		{
			return status.getText();
		}

		@Override
		public String getSourceString(DirectMessage msg)
		{
			return msg.getText();
		}
	};

	private String _displayName;

	private Enum_MuteSource_String(String displayName)
	{
		_displayName = displayName;
	}

	public String getDisplayName()
	{
		return _displayName;
	}

	public String getSourceString(Status status)
	{
		return null;
	}

	public String getSourceString(DirectMessage msg)
	{
		return null;
	}

	private static final Enum_MuteSource_String[] _values;
	static
	{
		_values = values();
	}
	public int getIndex()
	{
		return ordinal();
	}
	public static Enum_MuteSource_String value(int index)
	{
		return _values[index];
	}
}
