package com.Flanaria;

import android.view.View.OnTouchListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import twitter4j.UserStreamListener;

public interface IActivity_Mention extends UserStreamListener, ITapEventParent, OnItemSelectedListener,
OnItemLongClickListener, OnItemClickListener, OnTouchListener, ILateTapEventParent
{

}
