package com.Flanaria;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class WebChromeClient_Authorize extends WebChromeClient
{
	@Override
	public boolean onJsAlert(WebView view, String url, String message, JsResult result)
	{
		Retentioner_Authorize auth = CurrentTasks.getInstance().getCurrentAuthorize();
		auth.setPin(message);
		auth.getAuthorizeClient().dismissDialog();

		Observer_AuthorizeParent.getInstance().observe();

		result.confirm();

		// アラートダイアログをこのメソッド内で処理したか
		// falseを返すとAndroid APIによるデフォルトのアラートダイアログが表示されるため、今回はtrueを返す
		return true;
	}
}
