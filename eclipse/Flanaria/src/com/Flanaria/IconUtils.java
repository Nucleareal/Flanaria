package com.Flanaria;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import twitter4j.User;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class IconUtils
{
	public static Bitmap getUserIcon(User user)
	{
		long id = user.getId();
        Bitmap result = null;
        result = IconCache.getCache(id);
        if(result == null)
        {
	        try
	        {
	            result = BitmapFactory.decodeStream(new URL(user.getBiggerProfileImageURL()).openConnection().getInputStream());
	            IconCache.putCache(id, result);
	        }
	        catch (IOException e)
	        {
	        }
        }
        return result;
	}

	@Deprecated
	public static Drawable getUsersIcon(User user)
	{
		long userId = user.getId();
		Drawable result = null;
		if(IconCache.hasIconCache(userId))
		{
			result =  IconCache.getIconCache(userId);
		}
		else
		{
			try
			{
				InputStream is = (InputStream)fetch(new URL(user.getProfileImageURL()));
				result = Drawable.createFromStream(is, "src");
				IconCache.putIconCache(userId, result);
			}
			catch (Exception e)
			{
				Logger.e(e);
			}
		}
		return result;
	}

	@Deprecated
	private static Object fetch(URL address) throws IOException
	{
		Object content = address.getContent();
		return content;
	}

	@Deprecated
	public static void refreshIconCache(User user)
	{
		long id = user.getId();
		if(IconCache.hasIconCache(id))
		{
			IconCache.removeIconCache(id);
		}
		getUsersIcon(user);
	}

	public static void refresh(User user)
	{
		IconCache.removeCache(user.getId());
	}
}
