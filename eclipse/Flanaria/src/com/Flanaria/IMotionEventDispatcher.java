package com.Flanaria;

import java.util.Queue;

import android.view.MotionEvent;

public interface IMotionEventDispatcher
{
	void dispatchMotionEvent(Queue<MotionEvent> _queue);
}
