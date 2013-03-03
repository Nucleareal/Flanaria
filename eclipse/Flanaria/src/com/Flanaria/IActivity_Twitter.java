package com.Flanaria;

import twitter4j.UserStreamListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public interface IActivity_Twitter extends UserStreamListener, ITapEventParent, OnItemSelectedListener,
OnItemLongClickListener, OnItemClickListener, OnTouchListener, ILateTapEventParent
{

}
