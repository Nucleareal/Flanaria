package com.Flanaria;

import java.text.SimpleDateFormat;
import java.util.Locale;

import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.User;
import android.content.Context;
import android.text.Html;

public class ReplaceUtils implements IReplaceStrings
{
	public static String replaceUserNames(String string, Retentioner_Authorize auth)
	{
		return replaceUserNames(string,
				auth.getUser().getName(),
				auth.getUser().getScreenName(),
				String.valueOf(auth.getUser().getId()));
	}
	public static String replaceUserNames(String string, VerifiedUser user)
	{
		return replaceUserNames(string,
				user.getName(),
				user.getScreenName(),
				String.valueOf(user.getId()));
	}
	private static String replaceUserNames(String string, String Name, String ScreenName, String ID)
	{
		return string
				.replace(USER_NAME, Name)
				.replace(USER_SCREENNAME, ScreenName)
				.replace(USER_ID, ID);
	}
	public static String replaceTweetUserNames(String string, Status status)
	{
		return replaceUserNames(string,
				status.getUser().getName(),
				status.getUser().getScreenName(),
				String.valueOf(status.getUser().getId())
				);
	}

	public static String replaceTweetText(String string, Status status)
	{
		return replaceTweetText(string,
				status.getText());
	}
	private static String replaceTweetText(String string, String text)
	{
		String result = string.replace(TWEET_TEXT, text);
		if(FlanariaMorseParser.isableDecode(result))
		{
			result += "("+FlanariaMorseParser.decode(result)+")";
		}
		return result;
	}

	public static CharSequence replaceTweetVias(String string, Status status)
	{
		return replaceTweetVias(string,
				status.getSource(),
				new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(status.getCreatedAt())
				);
	}
	private static String replaceTweetVias(String string, String via, String time)
	{
		return Html.fromHtml(string
				.replace(TWEET_VIA, via)
				.replace(TWEET_TIME, time)).toString();
	}
	public static String toStatusURL(String string, Status status)
	{
		return string	.replace(USER_SCREENNAME, status.getUser().getScreenName())
						.replace(TWEET_ID, String.valueOf(status.getId()));
	}
	/*public static String replaceUsersNames(String string)
	{
		String s = Retentioner_Users.getInstance().getAllUserNamesWithAtt();
		return string.replace(USER_SCREENNAMES, s);
	}*/
	public static String replaceUsersNamesAsAll(String source)
	{
		String replace = Retentioner_Users.getInstance().getAllUserNamesWithAtt();
		return replaceUsersNames(source, replace);
	}
	public static String replaceUsersNamesAsActive(String source)
	{
		String replace = Retentioner_Users.getInstance().getAllActiveUserNamesWithAtt();
		return replaceUsersNames(source, replace);
	}
	public static String replaceUsersNames(String source, String replace)
	{
		return source.replace(USER_SCREENNAMES, replace);
	}

	public static String replaceMsgUserNames(String string, DirectMessage msg)
	{
		User s = msg.getSender();
		User r = msg.getRecipient();
		return replaceUserNames(string,
				s.getName()+"/"+r.getName(),
				s.getScreenName()+" to "+r.getScreenName(),
				String.valueOf(s.getId()));
	}
	public static String replaceMsgText(String string, DirectMessage msg)
	{
		return replaceTweetText(string, msg.getText());
	}
	public static String replaceMsgVias(String string, DirectMessage msg)
	{
		return replaceTweetVias(string,
				"Somewhere",
				new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(msg.getCreatedAt())
				).toString();
	}
	

	/*private static String setLinkToStatusURL(String time, Status status)
	{
		return "<a href=\"https://twitter.com/"+status.getUser().getScreenName()+"/status/"+status.getId()+"\">"+time+"</a>";
	}*/
	
	
	public static String elemName(TwitterElement elem, String string)
	{
		return replaceUserNames(
				string,
				elem.getUser().getName(),
				elem.getUser().getScreenName(),
				String.valueOf(elem.getUser().getId()) );
	}
	public static String elemText(TwitterElement elem, String string)
	{
		return replaceTweetText(
				string,
				elem.getText() );
	}
	public static String elemDate(TwitterElement elem, String string, Context context)
	{
		return replaceTweetVias(
				string,
				elem.getSource(context),
				new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPAN).format(elem.getCreatedAt()));
	}
}
