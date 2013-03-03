package com.Flanaria;

public class NumberUtils
{
	public static long toLong(String replace)
	{
		long l = -1L;
		try
		{
			l = Long.valueOf(replace).longValue();
		}
		catch(Exception e)
		{
			Logger.e(e);
		}
		return l;
	}
}
