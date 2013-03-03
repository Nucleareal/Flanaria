package com.Flanaria;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class ViewPager_Main extends ViewPager
{
	public ViewPager_Main(Context context, AttributeSet set)
	{
		super(context, set);
	}
	
	public ViewPager_Main(Context context)
	{
		super(context);
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4)
	{
		Logger.e(new Exception());
		super.onLayout(arg0, arg1, arg2, arg3, arg4);
	}
}
