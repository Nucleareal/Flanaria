package com.Flanaria;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewClient_Authorize extends WebViewClient
{
	private ProgressDialog _diag;

	public WebViewClient_Authorize()
	{
		CurrentTasks.getInstance().getCurrentAuthorize().setAuthorizeClient(this);
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url)
	{
		view.loadUrl(url);
		view.requestFocus(View.FOCUS_DOWN);
		return true;
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon)
	{
		if (_diag == null)
		{
			_diag = new ProgressDialog(view.getContext());
			_diag.setMessage(view.getResources().getString(R.string.WebView_Waiting));
			_diag.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		}
		_diag.show();
	}

	@Override
	public void onPageFinished(WebView view, String url)
	{
		_diag.hide();

		//HTMLソース上のpinコードを取得するためのJavaScript
		String script = "javascript:var elem = document.getElementsByTagName('code')[0]; if(elem) alert(elem.childNodes[0].nodeValue);";
		view.loadUrl(script);
	}

	public void dismissDialog()
	{
		_diag.dismiss();
	}
}
