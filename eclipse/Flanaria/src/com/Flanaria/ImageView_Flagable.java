package com.Flanaria;

import android.content.Context;
import android.widget.ImageView;

public class ImageView_Flagable extends ImageView
{
	private boolean _isLoaded;

	public ImageView_Flagable(Context context)
	{
		super(context);
	}

	public void setFlag(boolean flag)
	{
		_isLoaded = flag;
	}

	public boolean getFlag()
	{
		return _isLoaded;
	}
}
