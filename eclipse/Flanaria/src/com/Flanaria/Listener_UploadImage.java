package com.Flanaria;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Listener_UploadImage implements OnClickListener
{
	private Activity _parent;
	private ImageButton _button;

	public Listener_UploadImage(Activity activity, ImageButton _UploadImage)
	{
		_parent = activity;
		_button = _UploadImage;
	}

	public void onClick(View v)
	{
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		_parent.startActivityForResult(intent, 0);
		_button.setEnabled(false);
	}
}
