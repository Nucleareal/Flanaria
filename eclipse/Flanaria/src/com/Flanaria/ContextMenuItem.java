package com.Flanaria;

import twitter4j.Status;
import android.app.Activity;

public class ContextMenuItem
{
	private Enum_ContextMenu _menuitem;
	private String _extra;
	private Activity _parent;
	private Status _status;

	public ContextMenuItem(Activity parent, Enum_ContextMenu menuitem, Status status, String extra)
	{
		_menuitem = menuitem;
		_extra = extra;
		_parent = parent;
		_status = status;
	}

	public ContextMenuItem(Activity parent, Enum_ContextMenu menuitem, Status status)
	{
		this(parent, menuitem, status, menuitem.getDisplayName());
	}

	public String getDisplayName()
	{
		return _extra;
	}

	public int getIconResource()
	{
		return _menuitem.getIconResource();
	}

	public void onSelected()
	{
		AsyncTask_OnContextMenuSelected task = new AsyncTask_OnContextMenuSelected(_menuitem, _parent, _status, _extra);
		task.execute(_parent.getString(R.string.app_name));
	}
}
