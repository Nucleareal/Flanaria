package com.Flanaria.SunMoon;

import java.util.Calendar;
import com.Flanaria.CalcUtils;
import com.Flanaria.Logger;

import android.location.Location;

public class SunCalcurator
{
	private static final double PI = StrictMath.PI / 180D;
	private static final double converge = 0.00005D;
	private static final double R = 0.585556;
	private static double E;
	private static double K;
	private static double dT;

	public static SunRoundData SunMoon(Location location)
	{
		Calendar cal1 = Calendar.getInstance();
		int year = cal1.get(Calendar.YEAR);        //(2)現在の年を取得
	    int month = cal1.get(Calendar.MONTH) + 1;  //(3)現在の月を取得
	    int day = cal1.get(Calendar.DATE);         //(4)現在の日を取得
		double longitude = location.getLongitude();
		double latitude = location.getLatitude();
		double height = location.getAltitude();
		return SunMoon(year, month, day, longitude, latitude, height);
	}

	public static SunRoundData SunMoon(int year, int month, int day, double longitude, double latitude, double height)
	{
		Logger.d("Year:"+year);
		Logger.d("Month:"+month);
		Logger.d("day:"+day);
		E = 0.0353333 * StrictMath.sqrt(height);	//地平線伏角
		K = calc_K(year-2000, month, day);			//2000年1月1日力学時正午からの経過日数(日)
		dT =  ( 57 + 0.8 * (year - 1990) ) / 86400;	//地球自転遅れ補正値(日);

		SimplyDate Rs, Ss;
		Rs = calc_SUN(longitude, latitude, 0);	// 日出
		Ss = calc_SUN(longitude, latitude, 1);	// 日没
		SunRoundData data = new SunRoundData();
		data.sunapp = Rs;
		data.sundis = Ss;
		return data;
	}

	private static SimplyDate calc_SUN(double longitude, double latitude, int flag)
	{
		double[] ans = new double[2];
		double T,rm_sun,r_sun,theta;
		double S,W,k,dt;
		double dD = 1;	//補正値初期値
		double D = 0.5;	//逐次計算初期時刻(日)
		while( Math.abs(dD) > converge) // 逐次計算
		{
			T = ( K + D + dT ) / 365.25;	//Dの経過ユリウス年(日)
			rm_sun = lng_SUN(T);	//太陽の黄経
			r_sun = dist_SUN(T);	//太陽の距離
			theta = calc_THETA(longitude, T, D);	//恒星時
			ans = Kou2Seki(rm_sun, 0, T, ans);	//黄道 -> 赤道変換
			S = 0.266994 / r_sun;	//太陽の視半径
			W = 0.0024428 / r_sun;	//太陽の視差
			k = -S - R - E + W;	//太陽の出没高度
			dt = calc_dt(ans[0], ans[1], theta, k, latitude, flag);	//時角差(dt=tk-t)計算
			dD = dt / 360;	//仮定時刻に対する補正値
			D = D + dD;
		}
		return CalcUtils.toSimplyDate(D);
	}

	private static double[] Kou2Seki(double ramda, int beta, double T, double[] ans)
	{
		double U,V,W;
		double e = (23.439291 - .000130042 * T) * PI;	//黄道傾角
		double rm = ramda * PI;
		double bt = beta * PI;
		U = StrictMath.cos( bt ) * StrictMath.cos( rm );
		V =-StrictMath.sin( bt ) * StrictMath.sin( e ) + StrictMath.cos( bt ) * StrictMath.sin( rm ) * StrictMath.cos( e );
		W = StrictMath.sin( bt ) * StrictMath.cos( e ) + StrictMath.cos( bt ) * StrictMath.sin( rm ) * StrictMath.sin( e );
		ans[0] = V / U;
		ans[1] = W / StrictMath.sqrt( U * U + V * V );
		ans[0] = StrictMath.atan( ans[0] ) / PI;
		if (U < 0) ans[0] += 180;	//Uがマイナスのときは90<α<270 -> +180°
		ans[1] = StrictMath.atan( ans[1] ) / PI;
		return ans;
	}

	private static double calc_dt(double alpha, double delta, double theta, double k, double lat, int flag)
	{
		double dt,tk,t;
		if (flag == 2) { tk = 0; }	//南中の場合は天体の時角を返す
		else
		{
			tk  = StrictMath.sin(PI * k) - StrictMath.sin(PI * delta) * StrictMath.sin(PI * lat);
			tk /= StrictMath.cos(PI * delta) * StrictMath.cos(PI * lat);
			tk  = StrictMath.acos(tk) / PI;	//出没点の時角
			// tkは出のときマイナス、没のときプラス
			if(flag == 0 && tk > 0) { tk = -tk; }
			if(flag == 1 && tk < 0) { tk = -tk; }
		}
		t = theta - alpha;	//天体の時角
		dt = tk - t;
		// dtの絶対値を180°以下に調整
		if (dt >  180) { while ( dt >  180 ) { dt -= 360; } }
		if (dt < -180) { while ( dt < -180 ) { dt += 360; } }
		return dt ;
	}

	private static double calc_THETA(double lng, double T, double D)
	{
		return norm(325.4606 + 360.007700536*T + 0.00000003879*T*T + 360*D + lng);
	}

	private static double norm(double angle)
	{
		return angle - 360 * StrictMath.floor(angle / 360);
	}

	private static double dist_SUN(double T)
	{
		double r_sun;
		r_sun  = .000007 * StrictMath.sin( PI * norm( 156 + 329.6 * T ) );
		r_sun += .000007 * StrictMath.sin( PI * norm( 254 + 450.4 * T ) );
		r_sun += .000013 * StrictMath.sin( PI * norm( 27.8 + 4452.67 * T ) );
		r_sun += .000030 * StrictMath.sin( PI * norm( 90.0 ) );
		r_sun += .000091 * StrictMath.sin( PI * norm( 265.1 + 719.98 * T ) );
		r_sun += (.007256 - .0000002 * T) * StrictMath.sin( PI * norm( 267.54 + 359.991 * T ) ) ;
		r_sun = StrictMath.pow(10, r_sun);
		return r_sun;
	}

	private static double lng_SUN(double T)
	{
		double rm_sun;
		rm_sun  = .0003 * StrictMath.sin( PI * norm( 329.7 + 44.43 * T ) );
		rm_sun += .0003 * StrictMath.sin( PI * norm( 352.5 + 1079.97 * T ) );
		rm_sun += .0004 * StrictMath.sin( PI * norm( 21.1 + 720.02 * T ) );
		rm_sun += .0004 * StrictMath.sin( PI * norm( 157.3 + 299.30 * T ) );
		rm_sun += .0004 * StrictMath.sin( PI * norm( 234.9 + 315.56 * T ) );
		rm_sun += .0005 * StrictMath.sin( PI * norm( 291.2 + 22.81 * T ) );
		rm_sun += .0005 * StrictMath.sin( PI * norm( 207.4 + 1.50 * T ) );
		rm_sun += .0006 * StrictMath.sin( PI * norm( 29.8 + 337.18 * T ) );
		rm_sun += .0007 * StrictMath.sin( PI * norm( 206.8 + 30.35 * T ) );
		rm_sun += .0007 * StrictMath.sin( PI * norm( 153.3 + 90.38 * T ) );
		rm_sun += .0008 * StrictMath.sin( PI * norm( 132.5 + 659.29 * T ) );
		rm_sun += .0013 * StrictMath.sin( PI * norm( 81.4 + 225.18 * T ) );
		rm_sun += .0015 * StrictMath.sin( PI * norm( 343.2 + 450.37 * T ) );
		rm_sun += .0018 * StrictMath.sin( PI * norm( 251.3 + 0.20 * T ) );
		rm_sun += .0018 * StrictMath.sin( PI * norm( 297.8 + 4452.67 * T ) );
		rm_sun += .0020 * StrictMath.sin( PI * norm( 247.1 + 329.64 * T ) );
		rm_sun += .0048 * StrictMath.sin( PI * norm( 234.95 + 19.341 * T ) );
		rm_sun += .0200 * StrictMath.sin( PI * norm( 355.05 + 719.981 * T ) );
		rm_sun += (1.9146 - .00005 * T) * StrictMath.sin( PI * norm( 357.538 + 359.991 * T ) );
		rm_sun += norm( 280.4603 + 360.00769 * T );
		return rm_sun;
	}

	private static double calc_K(int yy, int mm, int dd)
	{
		double YY, MM, DD, K;
		YY = yy; MM = mm; DD = dd;
		// 1月、2月の場合は前年の13月、14月とみなして計算
		if( MM < 3.0 ){ YY -= 1.0; MM += 12.0; }
		K  = 365*YY + 30*MM + DD - 33.5 - 9/24;	//地方時:I=9
		K += Math.floor( 3*(MM + 1) / 5 );
		K += Math.floor( YY / 4 );
		return K ;
	}
}
