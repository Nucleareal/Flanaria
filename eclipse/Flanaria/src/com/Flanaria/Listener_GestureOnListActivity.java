package com.Flanaria;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.WindowManager;

public class Listener_GestureOnListActivity extends SimpleOnGestureListener
{
	private Activity _activity;
	private ITapEventParent _opParent;
	private float[] _X;
	private float[] _Y;
	private int _ScreenWidth;
	private int[] _Position;
	private ILateTapEventParent _LParent;

	public Listener_GestureOnListActivity(Activity activity, ITapEventParent opParent)
	{
		_activity = activity;
		_opParent = opParent;
		WindowManager wm = (WindowManager)_activity.getSystemService(Context.WINDOW_SERVICE);
		Display disp = wm.getDefaultDisplay();
		_ScreenWidth = disp.getWidth();
		disp.getHeight();
	}

	//OnTapSettings_{Type}_{Direction}

	private String getConvertToDirection(int direction)
	{
		switch(direction)
		{
			case 0 :
			{
				return "Left";
			}
			case 1 :
			{
				return "Middle";
			}
			case 2 :
			{
				return "Right";
			}
		}
		return "";
	}

	private void doOperation(String str)
	{
		Logger.d("Listener.DoOperation");
		str = "OnTapSettings_"+str;
		String value = Wrapper_SharedPreference.getInstance().getString(_activity, str);
		try
		{
			if(Integer.valueOf(value)-1 < 0)
			{
				return;
			}
			Enum_TweetTapOperation op = Enum_TweetTapOperation.indexOf(Integer.valueOf(value)-1);
			_opParent.setCurrentTapEvent(op);
			Logger.d("Setted:Event:"+op.toString());
			if(_LParent != null)
			{
				Logger.d("Exe:Lated Event");
				_LParent.doOperation();
				_LParent = null;
			}
			/*
			AsyncTask_TapOperationExecuter task = new AsyncTask_TapOperationExecuter(_activity, _statusGetter.getCurrentStatuses(), op);
			task.execute(_activity.getString(R.string.app_name));*/
		}
		catch(Exception e)
		{
			Logger.e(e);
		}
	}

	//短押し
	@Override
	public boolean onSingleTapConfirmed(MotionEvent event)
	{
		Logger.d("onSingleTap");

		getPositionAndStatuses(event);
		doOperation("SingleShort_"+getConvertToDirection(_Position[0]));
		return super.onSingleTapConfirmed(event);
	}

	//長押し
	@Override
	public void onLongPress(MotionEvent event)
	{
		Logger.d("onLongPress");

		getPositionAndStatuses(event);
		doOperation("SingleLong_"+getConvertToDirection(_Position[0]));
		super.onLongPress(event);
	}

	//ダブルタップ
	@Override
	public boolean onDoubleTapEvent(MotionEvent event)
	{
		Logger.d("onDoubleTap");

		getPositionAndStatuses(event);
		doOperation("Double_"+getConvertToDirection(_Position[0]));
		return super.onDoubleTapEvent(event);
	}

	//ドラッグ
	@Override
	public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY)
	{
		Logger.d("onFling");

		getPositionAndStatuses(event1, event2);
		if(_Position[0] != _Position[1])
		{
			doOperation("Fling_"+getConvertToDirection(_Position[0])+"To"+getConvertToDirection(_Position[1]));
		}
		return super.onFling(event1, event2, velocityX, velocityY);
	}

	@Override
	public boolean onDown(MotionEvent event)
	{
		Logger.d("onDown");

		return super.onDown(event);
	}

	@Override
	public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX, float distanceY)
	{
		//Logger.d("onScroll");

		return super.onScroll(event1, event2, distanceX, distanceY);
	}

	@Override
	public void onShowPress(MotionEvent event)
	{
		Logger.d("onShowPress");

		super.onShowPress(event);
	}

	@Override
	public boolean onSingleTapUp(MotionEvent event)
	{
		Logger.d("onSingleTapUp");

		return super.onSingleTapUp(event);
	}

	@Override
	public boolean onDoubleTap(MotionEvent event)
	{
		Logger.d("onDoubleTap");

		return super.onDoubleTap(event);
	}

	public void setLateTapEventParent(ILateTapEventParent parent)
	{
		Logger.d("setLateTapEvent");

		_LParent = parent;
	}

	private void getPositionAndStatuses(MotionEvent ... events)
	{
		_X = new float[events.length];
		_Y = new float[events.length];
		_Position = new int[events.length];
		for(int i = 0; i < events.length; i++)
		{
			MotionEvent event = events[i];
			_X[i] = event.getX();
			_Y[i] = event.getY();

			int xValue = Float.valueOf(_X[i]).intValue();
			_Position[i] = 0;
			if(xValue > _ScreenWidth/3)
			{
				_Position[i] = 1;
			}
			if(xValue > _ScreenWidth*2/3)
			{
				_Position[i] = 2;
			}
		}
	}
}
