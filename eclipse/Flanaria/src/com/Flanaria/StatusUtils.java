package com.Flanaria;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.FrameLayout;

public class StatusUtils implements IReplaceStrings
{
	public static Status[] getExtraStatus(Status status)
	{
		if(status.isRetweet())
		{
			return new Status[]{status.getRetweetedStatus()};
		}
		String[] urls = getURLAndMediaEntitiesAsArray(status);
		if(urls != null && urls.length > 0)
		{
			ArrayList<Status> list = new ArrayList<Status>();
			for(String url : urls)
			{
				if(url != null && url.contains("status"))
				{
					Pattern pattern = Pattern.compile("^/[0-9]+$");
					Matcher matcher = pattern.matcher(url);
					if(matcher.find())
					{
						long id = NumberUtils.toLong(matcher.group().replace("/", ""));
						if(id < 0)
						{
							continue;
						}
						Logger.d("Status "+status.getText()+" Has Extras:"+id);
						Twitter twitter = Retentioner_Users.getInstance().getAPITwitter();
						try
						{
							Status estatus = twitter.showStatus(id);
							list.add(estatus);
						}
						catch(TwitterException e)
						{
							Logger.e(e);
						}
					}
				}
			}
			Status[] result = list.toArray(new Status[list.size()]);
			return result;
		}
		return null;
	}

	public static String[] getURLAndMediaEntitiesAsArray(Status status)
	{
		URL[] urlEntity = getURLEntities(status);
		URL[] mediaEntity = getMediaEntities(status);
		int c = 0;
		if(urlEntity != null)
		{
			c += urlEntity.length;
		}
		if(mediaEntity != null)
		{
			c += mediaEntity.length;
		}
		String[] result = new String[c];
		int i = 0;
		if(urlEntity != null)
		{
			for(URL url : urlEntity)
			{
				if(url != null)
				{
					result[i++] = url.toString();
				}
			}
		}
		if(mediaEntity != null)
		{
			for(URL url : mediaEntity)
			{
				if(url != null)
				{
					result[i++] = url.toString();
				}
			}
		}
		return result;
	}

	public static URL[] getURLEntities(Status status)
	{
		URLEntity[] entities = status.getURLEntities();
		if(entities == null || entities.length <= 0)
		{
			return null;
		}
		URL[] result = new URL[entities.length];
		for(int i = 0; i < result.length; i++)
		{
			try
			{
				result[i] = URLUtils.expandUrl(new URL(entities[i].getExpandedURL()));
			}
			catch(Exception e)
			{
			}
		}
		return result;
	}

	public static URL[] getMediaEntities(Status status)
	{
		MediaEntity[] entities = status.getMediaEntities();
		if(entities == null || entities.length <= 0)
		{
			return null;
		}
		URL[] result = new URL[entities.length];
		for(int i = 0; i < result.length; i++)
		{
			try
			{
				result[i] = URLUtils.expandUrl(new URL(entities[i].getExpandedURL()));
			}
			catch(Exception e)
			{
			}
		}
		return result;
	}

	public static String[] getHashTagEntities(Status status)
	{
		HashtagEntity[] entities = status.getHashtagEntities();
		if(entities == null || entities.length <= 0)
		{
			return null;
		}
		String[] result = new String[entities.length];
		for(int i = 0; i < result.length; i++)
		{
			result[i] = entities[i].getText();
		}
		return result;
	}

	public static String[] getScreenNameEntities(Status status)
	{
		UserMentionEntity[] entities = status.getUserMentionEntities();
		if(entities == null || entities.length <= 0)
		{
			return null;
		}
		String[] result = new String[entities.length];
		for(int i = 0; i < result.length; i++)
		{
			result[i] = entities[i].getScreenName();
		}
		return result;
	}

	public static synchronized void insertStatus(Context context, Adapter_Tweet adapter, twitter4j.Status status)
	{
		if(FlanariaFilterController.getInstance().isShouldFilter(status))
		{
			return;
		}

		int i;
		Status _status = null;
		Date createdAt = status.getCreatedAt();
		Date _createdAt = null;
		int firstEqualsStatus = -1;
		for(i = 0; i < adapter.getCount(); i++)
		{
			_status = adapter.getItem(i);
			if(_status == null)
			{
				break;
			}
			_createdAt = _status.getCreatedAt();
			int var = _createdAt.compareTo(createdAt);
			if(var == 0)
			{
				if(firstEqualsStatus < 0)
				{
					firstEqualsStatus = i;
				}
				if(equalsStatusesNeighbor(_status, status))
				{
					return;
				}
			}
			if(var < 0)
			{
				break;
			}
		}
		if(firstEqualsStatus >= 0)
		{
			i = firstEqualsStatus;
		}
		adapter.insert(status, i);
		adapter.notifyDataSetChanged();
		CurrentTasks.getInstance().setInsertedNumber(i);
	}

	public static boolean equalsStatusesNeighbor(Status _status, Status status)
	{
		if(_status == null)
		{
			return status == null;
		}
		return 	status.getUser().getId() == _status.getUser().getId() &&
				status.getText().equals(_status.getText());
	}

	public static String getStatusURL(Context context, Status status)
	{
		String base = context.getString(R.string.Status_URL);
		return base	.replace(USER_SCREENNAME, status.getUser().getScreenName())
					.replace(TWEET_ID, String.valueOf(status.getId()));
	}

	public static String getSTOTText(Context context, Status status)
	{
		String base = context.getString(R.string.Status_STOT);
		return base	.replace(TWEET_URL, getStatusURL(context, status))
					.replace(TWEET_TEXT, status.getText())
					.replace(USER_SCREENNAME, status.getUser().getScreenName());
	}

	public static String getStatusUserScreenName(Status status)
	{
		return status.getUser().getScreenName();
	}

	public static boolean isThereInReplyTo(Status status)
	{
		return status.getInReplyToStatusId() > 0;
	}

	public static void setFrameColor(Context context, FrameLayout layout, Status status)
	{
		if(Retentioner_Users.getInstance().isUserScreenName(status.getUser().getScreenName()))
		{
			layout.setBackgroundColor(context.getResources().getColor(R.color.TweetFrame_MyTweet));
			return;
		}

		if(isMentionToMe(status))
		{
			layout.setBackgroundColor(context.getResources().getColor(R.color.TweetFrame_InReplyToMe));
			return;
		}
	}

	public static String getUnofficialString(Activity parent, Status status)
	{
		String prefix = parent.getString(R.string.Retweet_Prefix);
		prefix = ReplaceUtils.replaceTweetUserNames(prefix, status);
		return prefix+status.getText();
	}
	
	public static String getMentionRecivedScreenName(Status status)
	{
		String result = status.getInReplyToScreenName();
		String[] entities = getScreenNameEntities(status);
		if(entities != null)
		{
			for(String str : entities)
			{
				if(Retentioner_Users.getInstance().isUserScreenName(str))
				{
					result = str;
				}
			}
		}
		return result;
	}

	public static boolean isMentionToMe(Status status)
	{
		boolean result = Retentioner_Users.getInstance().isUserScreenName(status.getInReplyToScreenName());
		String[] entities = getScreenNameEntities(status);
		if(entities != null)
		{
			for(String str : entities)
			{
				result |= Retentioner_Users.getInstance().isUserScreenName(str);
			}
		}
		return result;
	}

	public static String getTextFromShareUri(Uri uri)
	{
		String res = uri.toString();
		if(!res.contains("twitter"))
		{
			return null;
		}
		try
		{
			res = URLDecoder.decode(res, "utf-8");
		}
		catch (UnsupportedEncodingException e)
		{
			Logger.e(e);
		}
		Pattern pat = Pattern.compile("https?.*?=");
		Matcher mat = pat.matcher(res);
		res = mat.replaceFirst("");
		return res;
	}
}
