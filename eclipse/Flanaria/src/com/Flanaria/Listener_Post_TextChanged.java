package com.Flanaria;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Listener_Post_TextChanged implements TextWatcher, OnItemClickListener, OnItemSelectedListener
{
	private Button _tweetButton;
	private TextView _characterCount;
	private Activity _parent;
	private AutoCompleteTextView _view;
	private EditText _editText;

	private AlertDialog _diag;

	public Listener_Post_TextChanged(Activity activity, EditText edit, Button button, TextView characterCount)
	{
		_editText = edit;
		_parent = activity;
		_tweetButton = button;
		_characterCount = characterCount;
	}

	private String _scr;

	public void afterTextChanged(Editable s)
	{
		if(s.toString().endsWith("@") && _scr.equals(s.toString()))
		{
			openComplete();
		}
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{
		_scr = s.toString();
	}

	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		int size = s.length();
		int left = 140 - size;
		//分割モードとかの処理
		if(left < 0 || size <= 0)
		{
			//_tweetButton.setEnabled(false);
		} else
		{
			_tweetButton.setEnabled(true);
		}
		_characterCount.setText(String.valueOf(left));
	}

	@SuppressLint("NewApi")
	private void openComplete()
	{
		AlertDialog.Builder builder = new Builder(_parent);

		LayoutInflater inflater = _parent.getLayoutInflater();
		LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.complete, null);

		_view = (AutoCompleteTextView)layout.findViewById(R.id.AutoComplete_Edit);
		_view.setThreshold(3);
		_view.setOnItemClickListener(this);
		_view.setOnItemSelectedListener(this);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(_parent, android.R.layout.simple_dropdown_item_1line, AutoComplete_Users.getInstance().getAllUsersScreenName());
		_view.setAdapter(adapter);

		builder.setCustomTitle(null);
		builder.setView(layout);

		_diag = builder.create();
		_diag.setOnShowListener(new OnShowListener()
		{
			public void onShow(DialogInterface dialog)
			{
				Logger.d("OnShow");
				InputMethodManager inputMethodManager = (InputMethodManager)_parent.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.showSoftInput(_view, 0);
			}
		});

		WindowManager.LayoutParams wmlp = _diag.getWindow().getAttributes();

		wmlp.gravity = Gravity.TOP;

		_diag.show();
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		_editText.setText(_editText.getText().toString()+_view.getText().toString()+" ");
		_editText.setSelection(_editText.getText().length());
		_diag.dismiss();
	}

	public void onNothingSelected(AdapterView<?> arg0)
	{
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		onItemSelected(arg0, arg1, arg2, arg3);
	}
}
