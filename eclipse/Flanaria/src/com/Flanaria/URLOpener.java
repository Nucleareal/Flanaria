package com.Flanaria;

import twitter4j.Status;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.net.Uri;

public class URLOpener
{
	private Status _status;
	private Activity _parent;
	private boolean _isFavstar;

	public URLOpener(Activity parent, Status status)
	{
		_parent = parent;
		_status = status;
	}

	public void open()
	{
		if(_isFavstar)
		{
			IntentUtils.intentOpenURL(_parent, Uri.parse(ReplaceUtils.toStatusURL(_parent.getString(R.string.FavstarStatusURL), _status)));
			return;
		}
		final String[] urls = StatusUtils.getURLAndMediaEntitiesAsArray(_status);
		if(urls == null || urls.length <= 0)
		{
			return;
		}
		if(urls.length == 1)
		{
			IntentUtils.intentOpenURL(_parent, Uri.parse(urls[0]));
			return;
		}

		Builder builder = new Builder(_parent);
		builder.setNegativeButton(_parent.getText(R.string.URL_Select_Cancel), null);
		builder.setItems(urls, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				IntentUtils.intentOpenURL(_parent, Uri.parse(urls[which]));
				dialog.dismiss();
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public URLOpener setFavstar()
	{
		_isFavstar = true;
		return this;
	}
}
