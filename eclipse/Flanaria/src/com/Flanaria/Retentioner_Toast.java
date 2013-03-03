package com.Flanaria;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Retentioner_Toast
{
	private Retentioner_Toast()
	{
	}
	private static Retentioner_Toast _ins;
	static
	{
		_ins = new Retentioner_Toast();
	}
	public static Retentioner_Toast getInstance()
	{
		return _ins;
	}

	private Toast _toast;

	public void showToast(Activity parent, String text, boolean isLong)
	{
		_toast = Toast.makeText(parent, text, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
		_toast.show();
	}

	public void showToastOnActivity(final Activity parent, final String text, final boolean isLong)
	{
		parent.runOnUiThread(new Runnable()
		{
			public void run()
			{
				showToast(parent, text, isLong);
			}
		});
	}

	public void showOperationFailedToast(Activity parent, boolean b)
	{
		showToast(parent, parent.getString(R.string.Operation_Failed), b);
	}

	public void showOperationFailedToastOnActivity(Activity parent, boolean b)
	{
		showToastOnActivity(parent, parent.getString(R.string.Operation_Failed), b);
	}

	public void showViewToast(Activity activity, LinearLayout layout, boolean isLong)
	{
		_toast = Toast.makeText(activity, "", isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
		_toast.setView(layout);
		_toast.show();
	}
}
