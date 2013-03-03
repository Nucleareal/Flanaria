package com.Flanaria;

import java.util.LinkedList;

import android.app.Activity;

public class Observer_AuthorizeParent
{
	private Observer_AuthorizeParent()
	{
	}
	private static Observer_AuthorizeParent _ins;
	static
	{
		_ins = new Observer_AuthorizeParent();
	}
	public static Observer_AuthorizeParent getInstance()
	{
		return _ins;
	}

	public void refresh()
	{
		list = new LinkedList<IAuthorizeParent>();
		list.clear();
	}

	public void addParent(IAuthorizeParent parent)
	{
		list.add(parent);
	}

	public void observe()
	{
		if(!(list != null && list.size() > 0))
		{
			return;
		}
		for(final IAuthorizeParent parent : list)
		{
			if(parent instanceof Activity)
			{
				((Activity)parent).runOnUiThread(new Runnable()
				{
					public void run()
					{
						parent.onAuthorizeFinished();
					}
				});
			} else
			{
				parent.onAuthorizeFinished();
			}
		}
	}

	private LinkedList<IAuthorizeParent> list;
}
