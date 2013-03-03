package com.Flanaria;

import com.Flanaria.SunMoon.SunRoundData;

import static java.lang.Float.NaN;

import android.content.Context;

public class Retentioner_Lovely
{
	private Float _Lovely;
	private Float _moonL;
	private Float _sunL;

	public void load(Context context)
	{
		_Lovely = Wrapper_SharedPreference.getInstance().getFloat(context, context.getString(R.string.Lovely_Text));
		check();
	}

	public void save(Context context)
	{
		Wrapper_SharedPreference.getInstance().putFloat(context, context.getString(R.string.Lovely_Text), _Lovely);
	}

	public void refresh()
	{
		_Lovely = 0F;
	}

	public void onSunData(SunRoundData data)
	{
		check();
	}

	public void onMoonData(double age)
	{
		_moonL = (float)StrictMath.sqrt(StrictMath.abs(15.0 - age));
		check();
	}

	private void check()
	{
		if(_Lovely != NaN && _moonL != NaN && _sunL != NaN)
		{
			_Lovely += _moonL;
			_Lovely += _sunL;
		}
	}

	private static final float[] bloods = new float[]
	{
		1, 2, 3, 5, 8, 12, 17,
	};
	private static final float[] rens = new float[]
	{
		1, 2, 3, 5, 8, 13, 21, 24, 27, 30,
	};

	{
		_Lovely = NaN;
		_moonL = NaN;
		_sunL = NaN;
	}

	//Lovely = 20 + ren(30) + blood(16) + moon(sqrt(7.5)) + time

	/* Singleton */

	private Retentioner_Lovely()
	{
	}
	private static Retentioner_Lovely _ins;
	static
	{
		_ins = new Retentioner_Lovely();
	}
	public static Retentioner_Lovely getInstance()
	{
		return _ins;
	}
}
