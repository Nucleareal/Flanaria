package com.Flanaria;

import android.content.res.Configuration;

public class Retentioner_Config
{
	private int _orient = -1;

	public boolean put(Configuration conf)
	{
		boolean result = false;
		if(_orient == -1)
		{
			_orient = conf.orientation;
			result = false;
		}
		else
		if((conf.orientation ^ _orient) != 0)
		{
			Logger.d("Changed Orientation ["+_orient+" to "+conf.orientation+"]");
			_orient = conf.orientation;
			result = true;
		}
		else
		{
			result = false;
		}
		return result;
	}

	public int getOrientation()
	{
		return _orient;
	}

	//
	
	private Retentioner_Config()
	{
		Logger.d("Staticed");
	}
	private static Retentioner_Config _ins = new Retentioner_Config();
	public static Retentioner_Config getInstance()
	{
		return _ins;
	}
}
