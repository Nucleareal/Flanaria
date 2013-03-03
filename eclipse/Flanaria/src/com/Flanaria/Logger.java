package com.Flanaria;

import android.util.*;

public class Logger
{

	public static void e(Exception e)
	{
		if (BuildConfig.DEBUG)
		{
			e.printStackTrace();
		}
	}

	public static void d(String message)
	{
		if (BuildConfig.DEBUG)
		{
			Log.d("Flanaria.Debug", message);
		}
	}

	public static void n(String message)
	{
		if(BuildConfig.DEBUG)
		{
			Log.d("Flanaria.Name", message);
		}
	}
}
