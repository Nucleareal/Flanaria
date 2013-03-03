package com.Flanaria;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class IntentUtils
{
	public static void intentWrap(final Activity activity, final String name)
	{
		activity.runOnUiThread(new Runnable()
		{
			public void run()
			{
				intent(activity, name);
			}
		});
	}

	public static void intentWrap(final Activity parent, final String name, final String[] keys, final String[] values)
	{
		parent.runOnUiThread(new Runnable()
		{
			public void run()
			{
				intent(parent, name, keys, values);
			}
		});
	}

	public static void intentWrapExtra(final Activity activity, final String name, final String extra)
	{
		activity.runOnUiThread(new Runnable()
		{
			public void run()
			{
				intentExtra(activity, name, extra);
			}
		});
	}

	public static void intent(Activity activity, String name)
	{
		intent(activity, name, new String[]{}, new String[]{});
	}

	public static void intent(Activity activity, String name, String[] keys, String[] values)
	{
		Intent intent = new Intent();
		intent.setClassName(activity.getPackageName(), activity.getPackageName()+"."+name);
		for(int i = 0; i < keys.length; i++)
		{
			String key = keys[i];
			String value = values[i];
			intent.putExtra(activity.getPackageName()+"."+name+"."+key, value);
		}
		activity.startActivity(intent);
	}

	public static void intentExtra(Activity activity, String name, String extra)
	{
		Intent intent = new Intent();
		intent.putExtra(Intent.EXTRA_TEXT, extra);
		intent.setClassName(activity.getPackageName(), activity.getPackageName()+"."+name);
		activity.startActivity(intent);
	}

	public static void intentOpenURL(Activity activity, Uri uri)
	{
		activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}

	public static void intentWrapTofuBuster(final Activity parent, final String text, final Boolean isCopyEnabled)
	{
		parent.runOnUiThread(new Runnable()
		{
			public void run()
			{
				intentTofuBuster(parent, text, isCopyEnabled);
			}
		});
	}

	public static void intentTofuBuster(Activity parent, String text, Boolean isCopyEnabled)
	{
		Intent i = new Intent(ACTION_SHOW_TEXT);
        i.putExtra(Intent.EXTRA_TEXT, text);
        i.putExtra(Intent.EXTRA_SUBJECT, parent.getText(R.string.app_name).toString());
        i.putExtra("isCopyEnabled", isCopyEnabled);
        parent.startActivity(i);
	}

	private static String ACTION_SHOW_TEXT = "com.product.kanzmrsw.tofubuster.ACTION_SHOW_TEXT";
}
