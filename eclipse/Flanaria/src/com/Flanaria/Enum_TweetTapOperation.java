package com.Flanaria;

import java.util.List;

import twitter4j.Status;
import android.app.Activity;
import android.content.Intent;

public enum Enum_TweetTapOperation
{
	Favorite(true)
	{
		@Override
		public void operation(Status status, Activity parent)
		{
			if(status.isRetweet())
			{
				status = status.getRetweetedStatus();
			}
			List<String> list = Retentioner_Users_Proxy.getInstance().favotiteStatusOnActive(status);
			String showtext = parent.getString(R.string.Post_Favorited);
			ToastUtils.showAsListOrFalseOnRun(parent, showtext, list, false);
		}
	},
	Reply(false)
	{
		@Override
		public void operation(Status status, Activity parent)
		{
			if(status.isRetweet())
			{
				status = status.getRetweetedStatus();
			}
			Intent intent = new Intent();
			intent.putExtra("InReplyToStatusId", status.getId());
			intent.putExtra(Intent.EXTRA_TEXT, "@"+status.getUser().getScreenName()+" ");
			intent.setClassName(parent.getPackageName(), parent.getPackageName()+".Activity_Post");
			parent.startActivity(intent);
		}
	},
	Retweet(true)
	{
		@Override
		public void operation(Status status, Activity parent)
		{
			if(status.isRetweet())
			{
				status = status.getRetweetedStatus();
			}
			List<String> list = Retentioner_Users_Proxy.getInstance().retweetStatusOnActive(status);
			String showtext = parent.getString(R.string.Post_Retweeted);
			ToastUtils.showAsListOrFalseOnRun(parent, showtext, list, false);
		}
	},
	OpenURL(false)
	{
		@Override
		public void operation(Status status, final Activity parent)
		{
			URLOpener opener = new URLOpener(parent, status);
			opener.open();
			return;
		}
	},
	ContextMenu(false)
	{
		@Override
		public void operation(Status status, Activity parent)
		{
			AsyncTask_ProgressDialog task0 = new AsyncTask_ProgressDialog(parent, parent.getString(R.string.Open_ContextMenu_Title), parent.getString(R.string.Open_ContextMenu_Summary));
			task0.execute(parent.getString(R.string.app_name));
			AsyncTask_OpenOptionMenu task1 = new AsyncTask_OpenOptionMenu(parent, status, task0);
			task1.execute(parent.getString(R.string.app_name));
		}
	},
	UnofficialRetweet(false)
	{
		@Override
		public void operation(Status status, Activity parent)
		{
			IntentUtils.intentExtra(parent, "Activity_Post", " "+parent.getText(R.string.Retweet_Prefix)+" "+status.getText());
		}
	},
	ShowUser(false)
	{
		@Override
		public void operation(Status status, Activity parent)
		{
			IntentUtils.intentExtra(parent, "Activity_ShowUser", parent.getText(R.string.Retweet_Prefix)+status.getText());
		}
	},
	RespectWithFavorite(true)
	{
		@Override
		public void operation(Status status, Activity parent)
		{
			if(status.isRetweet())
			{
				status = status.getRetweetedStatus();
			}
			List<String> list = Retentioner_Users_Proxy.getInstance().favotiteAndRespectStatusOnActive(status);
			String showtext = parent.getString(R.string.Post_FavoriteAndRespected);
			ToastUtils.showAsListOrFalseOnRun(parent, showtext, list, false);
		}
	},
	TofuBuster(false)
	{
		@Override
		public void operation(Status status, Activity parent)
		{
			if(status.isRetweet())
			{
				status = status.getRetweetedStatus();
			}
			final String showText = status.getText();
	        final Boolean isCopyEnabled = Boolean.valueOf(true);
	        IntentUtils.intentTofuBuster(parent, showText, isCopyEnabled);
		}
	},
	Share(false)
	{
		@Override
		public void operation(Status status, Activity parent)
		{
			IntentUtils.intentExtra(parent, "Activity_Post", StatusUtils.getSTOTText(parent, status));
		}
	},
	PostWithURL(false)
	{
		@Override
		public void operation(Status status, Activity parent)
		{
			IntentUtils.intentExtra(parent, "Activity_Post", StatusUtils.getStatusURL(parent, status));
		}
	},
	OpenFavstarURL(false)
	{
		@Override
		public void operation(Status status, Activity parent)
		{
			(new URLOpener(parent, status)).setFavstar().open();
		}
	},
	ShowConversation(false)
	{
		@Override
		public void operation(Status status, Activity parent)
		{
			CurrentTasks.getInstance().setCurrentConversationStatus(status);
			IntentUtils.intent(parent, "Activity_Conversation");
			//AsyncTask_CreateConversation task = new AsyncTask_CreateConversation(parent, status);
			//task.execute(parent.getString(R.string.app_name));
		}
	};

	private boolean _isBackground;

	private Enum_TweetTapOperation(boolean isBackground)
	{
		_isBackground = isBackground;
	}

	public void operation(Status status, Activity parent)
	{
		return;
	}

	public static Enum_TweetTapOperation indexOf(int index)
	{
		return values[index];
	}
	private static Enum_TweetTapOperation[] values;
	static
	{
		values = values();
	}

	public boolean isCanBackGround()
	{
		return _isBackground;
	}
}
