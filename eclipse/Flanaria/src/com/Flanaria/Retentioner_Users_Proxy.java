package com.Flanaria;

import java.io.InputStream;
import java.util.List;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Retentioner_Users_Proxy
{
	private Retentioner_Users_Proxy()
	{
	}
	private static Retentioner_Users_Proxy _ins;
	static
	{
		_ins = new Retentioner_Users_Proxy();
	}
	public static Retentioner_Users_Proxy getInstance()
	{
		return _ins;
	}

	public List<String> postStatusOnActive(String text, long inReplyToStatusId, InputStream img) throws NoAccountsActiveException
	{
		Logger.d("Retentioner_User_Proxy:InReplyToStatusId:"+inReplyToStatusId);
		return Retentioner_Users.getInstance().actionOnActive(new IActionOnUser()
		{
			public void action(Status status, String text, long inReplyToStatusId, Twitter twitter, VerifiedUser user, InputStream img) throws TwitterException
			{
				StatusUpdate statusUpdate = new StatusUpdate(text);
				Logger.d("Action:InReplyToStatusId:"+inReplyToStatusId);
				if(inReplyToStatusId >= 0)
				{
					Logger.d("Set Completed");
					statusUpdate.setInReplyToStatusId(inReplyToStatusId);
				}
				if(img != null)
				{
					statusUpdate.setMedia("image", img);
				}
				twitter.updateStatus(statusUpdate);
			}
		},
		null, text, inReplyToStatusId, img);
	}

	public List<String> favotiteStatusOnActive(Status status) throws NoAccountsActiveException
	{
		return Retentioner_Users.getInstance().actionOnActive(new IActionOnUser()
		{
			public void action(Status status, String text, long inReplyToStatusId, Twitter twitter, VerifiedUser user, InputStream img) throws TwitterException
			{
				long id = status.getId();
				twitter.createFavorite(id);
			}
		}, status, null, -1L, null);
	}

	public List<String> retweetStatusOnActive(Status status) throws NoAccountsActiveException
	{
		return Retentioner_Users.getInstance().actionOnActive(new IActionOnUser()
		{
			public void action(Status status, String text, long inReplyToStatusId, Twitter twitter, VerifiedUser user, InputStream img) throws TwitterException
			{
				long id = status.getId();
				twitter.retweetStatus(id);
			}
		}, status, null, -1L, null);
	}

	public List<String> favotiteAndRespectStatusOnActive(Status status) throws NoAccountsActiveException
	{
		long inReplyToStatusId = status.getInReplyToStatusId();
		return Retentioner_Users.getInstance().actionOnActive(new IActionOnUser()
		{
			public void action(Status status, String text, long inReplyToStatusId, Twitter twitter, VerifiedUser user, InputStream img) throws TwitterException
			{
				long id = status.getId();
				twitter.createFavorite(id);
				StatusUpdate statusUpdate = new StatusUpdate(status.getText());
				if(inReplyToStatusId > 0)
				{
					statusUpdate.setInReplyToStatusId(inReplyToStatusId);
				}
				twitter.updateStatus(statusUpdate);
			}
		}, status, null, inReplyToStatusId, null);
	}
}