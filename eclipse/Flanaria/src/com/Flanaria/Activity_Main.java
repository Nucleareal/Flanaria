package com.Flanaria;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class Activity_Main extends Activity
{
	private Context _con;
	private Adapter_ViewPager_Main _adapter;
	private ViewPager_Main _pager;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	 
	    _con = this;
	    _adapter = new Adapter_ViewPager_Main(_con);
	    _pager = (ViewPager_Main)findViewById(R.id.viewpager);
	    _pager.setAdapter(_adapter);
	    _pager.setCurrentItem(1, true);  
	}
}
