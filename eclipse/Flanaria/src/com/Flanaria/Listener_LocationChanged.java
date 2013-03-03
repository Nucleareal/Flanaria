package com.Flanaria;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class Listener_LocationChanged implements LocationListener
{
	private LocationManager _manager;
	private ILocationReciver _reciver;

	public Listener_LocationChanged(LocationManager locationManager, ILocationReciver reciver)
	{
		_manager = locationManager;
		_reciver = reciver;
	}

	public void onLocationChanged(Location location)
	{
		_reciver.onLocation(location);
		if(_manager != null)
		{
			_manager.removeUpdates(this);
			_manager = null;
		}
	}

	public void onProviderDisabled(String arg0)
	{
	}

	public void onProviderEnabled(String arg0)
	{
	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2)
	{
	}
}
