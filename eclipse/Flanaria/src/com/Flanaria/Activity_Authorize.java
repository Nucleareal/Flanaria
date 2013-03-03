package com.Flanaria;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class Activity_Authorize extends Activity implements IAuthorizeParent, IAsyncTaskParent
{
	private WebView _webView;
	private Retentioner_Authorize _auth;

	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Observer_AuthorizeParent.getInstance().refresh();
        Observer_AuthorizeParent.getInstance().addParent(this);
        viewInitialize();
        showPINCode();
    }

	private void viewInitialize()
	{
		setContentView(R.layout.authorize);
		final Activity parent = this;
		((Button)findViewById(R.id.Authorize_SubmitButton)).setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				parent.runOnUiThread(new Runnable()
				{
					public void run()
					{
						_auth.setPin(((EditText)parent.findViewById(R.id.Authorize_PinField)).getText().toString());
						authorize();
					}
				});
			}
		});
	}

	private void showPINCode()
	{
		AsyncTask_RegisterAccount task = new AsyncTask_RegisterAccount(this, _auth, this);
		task.execute(getString(R.string.app_name));
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if(_webView != null)
		{
			_webView.destroy();
		}
	}

	private void authorize()
	{
		final Activity activity = this;

		_auth.setState(Enum_AuthorizeState.Authorizing);

		AsyncTask_Authorize authorize = new AsyncTask_Authorize(this);
		authorize.execute(getResources().getString(R.string.app_name));

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.Authorize_ReDialog_Title);
		builder.setMessage(R.string.Authorize_ReDialog_Message);
		builder.setPositiveButton(R.string.Authorize_ReDialog_Yes,
		new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				IntentUtils.intent(activity, "Activity_Authorize");
				dialog.dismiss();
				finish();
			}
		});
		builder.setNegativeButton(R.string.Authorize_ReDialog_No,
		new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				activity.finish();
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void onAuthorizeFinished()
	{
		String pin = CurrentTasks.getInstance().getCurrentAuthorize().getPin();
		((EditText)findViewById(R.id.Authorize_PinField)).setText(pin);
	}

	public void onTaskCompleted(AsyncTask<?, ?, ?> task)
	{
		if(task instanceof AsyncTask_RegisterAccount)
		{
			AsyncTask_RegisterAccount ret = ((AsyncTask_RegisterAccount)task);
			_auth = ret.getRetentionerAuth();
			_webView = ret.getWebView();
		}
	}
}
