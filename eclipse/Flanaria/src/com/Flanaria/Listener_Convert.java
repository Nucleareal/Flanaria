package com.Flanaria;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class Listener_Convert implements OnClickListener
{
	private EditText _text;

	public Listener_Convert(EditText TextBox)
	{
		_text = TextBox;
	}

	public void onClick(View v)
	{
		_text.setText(FlanariaMorseParser.encode(_text.getText().toString()));
	}
}
