package com.Flanaria;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.URLEntity;

public class Utils_TwitterElement
{
	public static int getColor(TwitterElement elem)
	{
		if(elem.getType().equals(Enum_ElementType.DirectMessage))
		{
			return R.color.TweetFrame_DirectMessage;
		}
		
		//Status
		
		if(Retentioner_Users.getInstance().isUserScreenName(elem.getUser().getScreenName()))
		{
			return R.color.TweetFrame_MyTweet;
		}

		if(StatusUtils.isMentionToMe(elem.getStatus()))
		{
			return R.color.TweetFrame_InReplyToMe;
		}
		
		return R.color.TweetFrame_Default;
	}

	public static TwitterElement[] getExtraElements(TwitterElement elem)
	{
		LinkedList<TwitterElement> list = new LinkedList<TwitterElement>();
		
		switch(elem.getType())
		{
			case Status:
			{
				Status status = elem.getStatus();
				if(status.isRetweet())
				{
					list.add(new TwitterElement(status.getRetweetedStatus()));
				}
				break;
			}
			case DirectMessage:
			{
				break;
			}
		}
		List<String> urls = getURLEntities(elem);
		for(String s : urls)
		{
			String result = null;
			if(result == null) result = getMatchedResult("(?=.*twitter.com/.*status/.*)[0-9]*$", s);
			if(result == null) result = getMatchedResult("(?=.*favstar.fm/.*)[0-9]*$", s);
			if(result != null)
			{
				try
				{
					Status status = Retentioner_Users.getInstance().getAPITwitter().showStatus(Long.valueOf(result).longValue());
					list.add(new TwitterElement(status));
				}
				catch(Exception e)
				{
					Logger.e(e);
				}
			}
		}
		TwitterElement[] result = list.toArray(new TwitterElement[list.size()]);
		return result;
	}

	private static String getMatchedResult(String regex, String s)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s);
		String result = null;
		if(matcher.find())
		{
			result = matcher.group();
		}
		return result;
	}

	private static List<String> getURLEntities(TwitterElement elem)
	{
		LinkedList<String> result = new LinkedList<String>();
		URLEntity[] entities = null;
		switch(elem.getType())
		{
			case Status:
			{
				entities = elem.getStatus().getURLEntities();
				break;
			}
			case DirectMessage:
			{
				entities = elem.getDirectMessage().getURLEntities();
				break;
			}
		}
		if(entities != null)
		{
			for(URLEntity url : entities)
				result.add(url.getExpandedURL());
		}
		return result;
	}

	public static void insert(Adapter_TwitterElement adapter, Status	status)
	{
		TwitterElement elem = new TwitterElement(status);
		
		insert(adapter, elem);
	}
	
	public static void insert(Adapter_TwitterElement adapter, DirectMessage msg)
	{
		TwitterElement elem = new TwitterElement(msg);
		
		insert(adapter, elem);
	}

	public static void insert(Adapter_TwitterElement adapter, TwitterElement elem)
	{
		if(FlanariaFilterController.getInstance().isShouldFilter(elem))
		{
			return;
		}

		int i;
		TwitterElement elem1 = null;
		Date createdAt = elem.getCreatedAt();
		Date _createdAt = null;
		int firstEqualsStatus = -1;
		for(i = 0; i < adapter.getCount(); i++)
		{
			elem1 = adapter.getItem(i);
			if(elem1 == null)
			{
				break;
			}
			_createdAt = elem1.getCreatedAt();
			int var = _createdAt.compareTo(createdAt);
			if(var == 0)
			{
				if(firstEqualsStatus < 0)
				{
					firstEqualsStatus = i;
				}
				if(equalsElementsNeighbor(elem1, elem))
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
		adapter.insert(elem, i);
		adapter.notifyDataSetChanged();
		CurrentTasks.getInstance().setInsertedNumber(i);
	}
	
	public static boolean equalsElementsNeighbor(TwitterElement elem, TwitterElement elem1)
	{
		if(elem == null)
		{
			return elem1 == null;
		}
		return 	elem1.getUser().getId() == elem.getUser().getId() &&
				elem1.getText().equals(elem.getText());
	}
}
