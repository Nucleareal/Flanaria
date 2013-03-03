package com.Flanaria;

import java.util.List;

import twitter4j.Status;
import android.app.Activity;
import android.net.Uri;

public enum Enum_ContextMenu
{
	Favorite("ふぁぼ")
	{
		@Override
		public void onSelected(Activity parent, Status status, String text)
		{
			List<String> list = Retentioner_Users_Proxy.getInstance().favotiteStatusOnActive(status);
			String showtext = parent.getString(R.string.Post_Favorited);
			ToastUtils.showAsListOrFalseOnRun(parent, showtext, list, false);
		}

		@Override
		public int getIconResource()
		{
			return R.drawable.rating_important;
		}
	},
	Reply("リプライ")
	{
		@Override
		public void onSelected(Activity parent, Status status, String text)
		{
			Enum_TweetTapOperation.Reply.operation(status, parent);
		}

		@Override
		public int getIconResource()
		{
			return R.drawable.social_reply;
		}
	},
	PostWithURL("URL引用")
	{
		@Override
		public void onSelected(Activity parent, Status status, String text)
		{
			IntentUtils.intentExtra(parent, "Activity_Post", StatusUtils.getStatusURL(parent, status));
		}

		@Override
		public int getIconResource()
		{
			return R.drawable.content_edit;
		}
	},
	UnofficialRetweet("非公式RT")
	{
		@Override
		public void onSelected(Activity parent, Status status, String text)
		{
			if(status.isRetweet())
			{
				status = status.getRetweetedStatus();
			}
			IntentUtils.intentExtra(parent, "Activity_Post", StatusUtils.getUnofficialString(parent, status));
		}

		@Override
		public int getIconResource()
		{
			return R.drawable.content_edit;
		}
	},
	TofuBuster("TofuBuster")
	{
		@Override
		public void onSelected(Activity parent, Status status, String text)
		{
			if(status.isRetweet())
			{
				status = status.getRetweetedStatus();
			}
			final String showText = status.getText();
            final Boolean isCopyEnabled = Boolean.valueOf(true);
            IntentUtils.intentTofuBuster(parent, showText, isCopyEnabled);
		}

		@Override
		public int getIconResource()
		{
			return R.drawable.action_about;
		}
	},
	Share("共有")
	{
		@Override
		public void onSelected(Activity parent, Status status, String text)
		{
			IntentUtils.intentExtra(parent, "Activity_Post", StatusUtils.getSTOTText(parent, status));
		}

		@Override
		public int getIconResource()
		{
			return R.drawable.social_share;
		}
	},
	//textでURLが与えられます
	URL(null)
	{
		@Override
		public void onSelected(Activity parent, Status status, String text)
		{
			IntentUtils.intentOpenURL(parent, Uri.parse(text));
		}

		@Override
		public int getIconResource()
		{
			return R.drawable.location_web_site;
		}
	},
	//textでHashTagが与えられます
	HashTag(null)
	{
		@Override
		public void onSelected(Activity parent, Status status, String text)
		{
			IntentUtils.intentExtra(parent, "Activity_Post", " "+text);
		}

		@Override
		public int getIconResource()
		{
			return R.drawable.content_edit;
		}
	},
	//textでUserが与えられます
	User(null)
	{
		@Override
		public void onSelected(Activity parent, Status status, String text)
		{
			text = text.replace("@", "");
			IntentUtils.intentExtra(parent, "Activity_ShowUser", text);
		}

		@Override
		public int getIconResource()
		{
			return R.drawable.social_person;
		}
	},
	ShowConversation("会話を表示")
	{
		@Override
		public boolean isShouldShow(Status status)
		{
			return status.getInReplyToScreenName() != null;
		}

		@Override
		public void onSelected(Activity parent, Status status, String text)
		{
			CurrentTasks.getInstance().setCurrentConversationStatus(status);
			IntentUtils.intent(parent, "Activity_Conversation");
			//AsyncTask_CreateConversation task = new AsyncTask_CreateConversation(parent, status);
			//task.execute(parent.getString(R.string.app_name));
		}

		@Override
		public int getIconResource()
		{
			return R.drawable.device_access_storage;
		}
	};

	private String _displayName;

	public boolean isShouldShow(Status status)
	{
		return true;
	}

	private Enum_ContextMenu(String DisplayName)
	{
		_displayName = DisplayName;
	}

	public String getDisplayName()
	{
		return _displayName;
	}

	public void onSelected(Activity parent, Status status, String text)
	{
		return;
	}

	public int getIconResource()
	{
		return R.drawable.action_help;
	}

	public Enum_ContextMenu indexOf(int index)
	{
		return values[index];
	}
	private static Enum_ContextMenu[] values;
	static
	{
		values = values();
	}

	private static Enum_ContextMenu[] _defaultValues;
	static
	{
		_defaultValues = new Enum_ContextMenu[]
		{
			Favorite,
			Reply,
			PostWithURL,
			UnofficialRetweet,
			TofuBuster,
			Share,
		};
	}
	public static Enum_ContextMenu[] getDefaultValues()
	{
		return _defaultValues;
	}
}
