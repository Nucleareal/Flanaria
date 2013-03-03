package com.Flanaria;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Wrapper_SharedPreference
{
	private Wrapper_SharedPreference()
	{
	}
	private static Wrapper_SharedPreference _ins;
	static
	{
		_ins = new Wrapper_SharedPreference();
	}
	public static Wrapper_SharedPreference getInstance()
	{
		return _ins;
	}

	private SharedPreferences getSP(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
	private Editor getEditor(Context context)
	{
		return getSP(context).edit();
	}

	public String getString(Context context, String key)
	{
		return getString(context, key, "");
	}
	public String getString(Context context, String key, String defvalue)
	{
		return getSP(context).getString(key, defvalue);
	}
	public int getInteger(Context context, String key)
	{
		return getInteger(context, key, 0);
	}
	public int getInteger(Context context, String key, int defvalue)
	{
		return getSP(context).getInt(key, defvalue);
	}
	public boolean getBoolean(Context context, String key)
	{
		return getBoolean(context, key, false);
	}
	public boolean getBoolean(Context context, String key, boolean defvalue)
	{
		return getSP(context).getBoolean(key, defvalue);
	}
	public long getLong(Context context, String key)
	{
		return getLong(context, key, 0L);
	}
	public long getLong(Context context, String key, long defvalue)
	{
		return getSP(context).getLong(key, defvalue);
	}
	public float getFloat(Context context, String key)
	{
		return getFloat(context, key, 0F);
	}
	public float getFloat(Context context, String key, float defvalue)
	{
		return getSP(context).getFloat(key, defvalue);
	}

	public boolean putString(Context context, String key, String value)
	{
		return getEditor(context).putString(key, value).commit();
	}
	public boolean putInteger(Context context, String key, int value)
	{
		return getEditor(context).putInt(key, value).commit();
	}
	public boolean putLong(Context context, String key, long value)
	{
		return getEditor(context).putLong(key, value).commit();
	}
	public boolean putBoolean(Context context, String key, boolean value)
	{
		return getEditor(context).putBoolean(key, value).commit();
	}
	public boolean putFloat(Context context, String key, float value)
	{
		return getEditor(context).putFloat(key, value).commit();
	}
}
