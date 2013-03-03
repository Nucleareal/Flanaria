package com.Flanaria;

import android.app.AlertDialog;
import android.content.Context;

public class DialogUtils
{
	public static AlertDialog.Builder createDialogBuilder(Context context, String title, String message)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		return builder;
	}
}
