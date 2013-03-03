package com.Flanaria.SunMoon;

import java.util.Date;

public class MoonCalcurator
{
	private static double getNewMoon(double julian)
	{
		double k     = Math.floor((julian - 2451550.09765) / 29.530589);
		double t     = k / 1236.85;
		double nmoon = 2451550.09765
					 + 29.530589  * k
					 +  0.0001337 * t * t
					 -  0.40720   * Math.sin((201.5643 + 385.8169 * k) * 0.017453292519943)
					 +  0.17241   * Math.sin((2.5534 +  29.1054 * k) * 0.017453292519943);
		return (nmoon);         // julian - nmoonが現在時刻の月齢
	}

	private static double getJulian(Date date)
	{
		return date.getTime() / 86400000.0+2440587.5;
	}

	public static double getMoonAge(Date date)
	{
		double julian = getJulian(date);
		double nmoon = getNewMoon(julian);
		if (nmoon > julian)
		{
			nmoon = getNewMoon(julian - 1.0);
		}
		return julian - nmoon;
	}
}
