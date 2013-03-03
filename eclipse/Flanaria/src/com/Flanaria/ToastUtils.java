package com.Flanaria;

import java.util.List;

import android.app.Activity;
import android.location.Location;
import android.widget.LinearLayout;

public class ToastUtils
{
	public static void showAsListOrFalseOnRun(final Activity parent, final String showtext, final List<String> list, final boolean isLong)
	{
		parent.runOnUiThread(new Runnable()
		{
			public void run()
			{
				showAsListOrFalse(parent, showtext, list, isLong);
			}
		});
	}

	public static void showAsListOrFalse(Activity parent, String source, List<String> replace, boolean isLong)
	{
		if(replace != null && replace.size() >= 1)
		{
			Retentioner_Toast.getInstance().showToast(parent,
					ReplaceUtils.replaceUsersNames(source, ConnectUtils.connect(replace, "@", ", ")),
					false);
		}
		else
		{
			Retentioner_Toast.getInstance().showOperationFailedToast(parent, false);
		}
	}

	public static void showLocation(Activity activity, Location location, boolean b)
	{
		String s = location.getLatitude()+", "+location.getLongitude()+", "+location.getAltitude();
		Retentioner_Toast.getInstance().showToast(activity, s, b);
	}

	public static void showLocationOnRun(final Activity activity, final Location location, final boolean b)
	{
		activity.runOnUiThread(new Runnable()
		{
			public void run()
			{
				showLocation(activity, location, b);
			}
		});
	}

	public static void showViewToast(Activity activity, LinearLayout layout, boolean isLong)
	{
		Retentioner_Toast.getInstance().showViewToast(activity, layout, isLong);
	}
	
	public static void showViewToastOnActivity(final Activity activity, final LinearLayout layout, final boolean isLong)
	{
		activity.runOnUiThread(new Runnable()
		{
			public void run()
			{
				showViewToast(activity, layout, isLong);
			}
		});
	}
}
