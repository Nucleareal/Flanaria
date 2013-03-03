package com.Flanaria;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class IconCache
{
	private static HashMap<Long, Bitmap> _cah;
	private static HashMap<Long, Drawable> _cache;
	static
	{
		_cache = new HashMap<Long, Drawable>();
		_cah = new HashMap<Long, Bitmap>();
	}

	public static void clearCache()
	{
		_cache.clear();
		_cah.clear();
	}

	@Deprecated
	public static Drawable getIconCache(long id)
	{
		return _cache.get(Long.valueOf(id));
	}

	@Deprecated
	public static void putIconCache(long id, Drawable drawable)
	{
		_cache.put(Long.valueOf(id), drawable);
	}

	@Deprecated
	public static boolean hasIconCache(long id)
	{
		return _cache.containsKey(Long.valueOf(id));
	}

	@Deprecated
	public static void removeIconCache(long id)
	{
		_cache.remove(Long.valueOf(id));
	}

	public static Bitmap getCache(long id)
	{
		return _cah.get(Long.valueOf(id));
	}

	public static void putCache(long id, Bitmap result)
	{
		_cah.put(Long.valueOf(id), result);
	}

	public static void removeCache(long id)
	{
		_cah.remove(Long.valueOf(id));
	}
}
