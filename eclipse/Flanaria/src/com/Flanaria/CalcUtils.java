package com.Flanaria;

import java.util.StringTokenizer;

import com.Flanaria.SunMoon.SimplyDate;

public class CalcUtils
{
	public static int getIntegerInDouble(double d)
	{
		String s = String.valueOf(d);
		StringTokenizer sz = new StringTokenizer(s, ".");
		return Integer.valueOf(sz.nextToken());
	}

	public static SimplyDate toSimplyDate(double d)
	{
		SimplyDate result = new SimplyDate();

		d *= 24;
		int hour = CalcUtils.getIntegerInDouble(d);
		d -= hour; d *= 60;
		int minute = CalcUtils.getIntegerInDouble(d);
		d -= minute; d *= 60;
		int second = CalcUtils.getIntegerInDouble(d);
		d -= second; d *= 1000;
		int millsec = CalcUtils.getIntegerInDouble(d);

		result.hour = hour;
		result.minute = minute;
		result.second = second;
		result.millsec = millsec;

		return result;
	}
}
