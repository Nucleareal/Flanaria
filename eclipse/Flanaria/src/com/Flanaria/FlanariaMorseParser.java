package com.Flanaria;

import java.util.LinkedHashMap;

public class FlanariaMorseParser
{
	public static String encode(String rawText)
	{
		String result = "";
		for(int i = 0; i < rawText.length(); i++)
		{
			char c = rawText.charAt(i);
			String rep = pToF.get(String.valueOf(c));
			if(rep != null)
			{
				result += rep + " ";
			}
			else
			{
				result += c;
			}
		}
		result += " ♪";
		return result;
	}

	public static boolean isableDecode(String s)
	{
		return s.endsWith(" ♪");
	}

	public static String decode(String flanariaText)
	{
		String result = "";
		String[] remov = flanariaText.split(" ");
		for(String s : remov)
		{
			if(s.equals(remov[remov.length-1])) continue;
			String str = fToP.get(s);
			if(str != null)
			{
				result += str;
			}
			else
			{
				result += s;
			}
		}
		return result;
	}

	private static LinkedHashMap<String, String>pToF;
	private static LinkedHashMap<String, String>fToP;
	private static LinkedHashMap<Character, Boolean> cToI;

	@SuppressWarnings("unchecked")
	private static ObjectPair<String, String>[] gentable = new ObjectPair[]
	{
		new ObjectPair<String, String>("い", "ラ"),
		new ObjectPair<String, String>("う", "ー"),
		new ObjectPair<String, String>("ん", "ララ"),
		new ObjectPair<String, String>("か", "ラー"),
		new ObjectPair<String, String>("し", "ーラ"),
		new ObjectPair<String, String>("と", "ーー"),
		new ObjectPair<String, String>("て", "ラララ"),
		new ObjectPair<String, String>("の", "ララー"),
		new ObjectPair<String, String>("な", "ラーラ"),
		new ObjectPair<String, String>("た", "ラーー"),
		new ObjectPair<String, String>("は", "ーララ"),
		new ObjectPair<String, String>("に", "ーラー"),
		new ObjectPair<String, String>("き", "ーーラ"),
		new ObjectPair<String, String>("で", "ーーー"),
		new ObjectPair<String, String>("す", "ララララ"),
		new ObjectPair<String, String>("く", "ラララー"),
		new ObjectPair<String, String>("る", "ララーラ"),
		new ObjectPair<String, String>("、", "ララーー"),
		new ObjectPair<String, String>("が", "ラーララ"),
		new ObjectPair<String, String>("ま", "ラーラー"),
		new ObjectPair<String, String>("も", "ラーーラ"),
		new ObjectPair<String, String>("こ", "ラーーー"),
		new ObjectPair<String, String>("っ", "ーラララ"),
		new ObjectPair<String, String>("じ", "ーララー"),
		new ObjectPair<String, String>("つ", "ーラーラ"),
		new ObjectPair<String, String>("り", "ーラーー"),
		new ObjectPair<String, String>("。", "ーーララ"),
		new ObjectPair<String, String>("ょ", "ーーラー"),
		new ObjectPair<String, String>("れ", "ーーーラ"),
		new ObjectPair<String, String>("を", "ーーーー"),
		new ObjectPair<String, String>("ー", "ラララララ"),
		new ObjectPair<String, String>("お", "ララララー"),
		new ObjectPair<String, String>("だ", "ラララーラ"),
		new ObjectPair<String, String>("あ", "ラララーー"),
		new ObjectPair<String, String>("ら", "ララーララ"),
		new ObjectPair<String, String>("け", "ララーラー"),
		new ObjectPair<String, String>("さ", "ララーーラ"),
		new ObjectPair<String, String>("よ", "ララーーー"),
		new ObjectPair<String, String>("ゅ", "ラーラララ"),
		new ObjectPair<String, String>("ど", "ラーララー"),
		new ObjectPair<String, String>("ち", "ラーラーラ"),
		new ObjectPair<String, String>("せ", "ラーラーー"),
		new ObjectPair<String, String>("そ", "ラーーララ"),
		new ObjectPair<String, String>("え", "ラーーラー"),
		new ObjectPair<String, String>("み", "ラーーーラ"),
		new ObjectPair<String, String>("ひ", "ラーーーー"),
		new ObjectPair<String, String>("め", "ーララララ"),
		new ObjectPair<String, String>("ほ", "ーラララー"),
		new ObjectPair<String, String>("ふ", "ーララーラ"),
		new ObjectPair<String, String>("わ", "ーララーー"),
		new ObjectPair<String, String>("ば", "ーラーララ"),
		new ObjectPair<String, String>("ろ", "ーラーラー"),
		new ObjectPair<String, String>("や", "ーラーーラ"),
		new ObjectPair<String, String>("ゆ", "ーラーーー"),
		new ObjectPair<String, String>("ぶ", "ーーラララ"),
		new ObjectPair<String, String>("び", "ーーララー"),
		new ObjectPair<String, String>("ぎ", "ーーラーラ"),
		new ObjectPair<String, String>("ず", "ーーラーー"),
		new ObjectPair<String, String>("ね", "ーーーララ"),
		new ObjectPair<String, String>("ご", "ーーーラー"),
		new ObjectPair<String, String>("ゃ", "ーーーーラ"),
		new ObjectPair<String, String>("む", "ーーーーー"),
		new ObjectPair<String, String>("へ", "ララララララ"),
		new ObjectPair<String, String>("げ", "ラララララー"),
		new ObjectPair<String, String>("ぼ", "ララララーラ"),
		new ObjectPair<String, String>("・", "ララララーー"),
		new ObjectPair<String, String>("べ", "ラララーララ"),
		new ObjectPair<String, String>("ぷ", "ラララーラー"),
		new ObjectPair<String, String>("ぜ", "ラララーーラ"),
		new ObjectPair<String, String>("ざ", "ラララーーー"),
		new ObjectPair<String, String>("ぐ", "ララーラララ"),
		new ObjectPair<String, String>("ば", "ララーララー"),
		new ObjectPair<String, String>("ぞ", "ララーラーラ"),
		new ObjectPair<String, String>("づ", "ララーラーー"),
		new ObjectPair<String, String>("？", "ララーーララ"),
		new ObjectPair<String, String>("ぴ", "ララーーラー"),
		new ObjectPair<String, String>("ぽ", "ララーーーラ"),
		new ObjectPair<String, String>("ぁ", "ララーーーー"),
		new ObjectPair<String, String>("ぇ", "ラーララララ"),
		new ObjectPair<String, String>("ぺ", "ラーラララー"),
		new ObjectPair<String, String>("ぃ", "ラーララーラ"),
		new ObjectPair<String, String>("！", "ラーララーー"),
		new ObjectPair<String, String>("ぉ", "ラーラーララ"),
		new ObjectPair<String, String>("～", "ラーラーラー"),
		new ObjectPair<String, String>("、", "ラーラーーラ"),
		new ObjectPair<String, String>("ぬ", "ラーラーーー"),
		new ObjectPair<String, String>("ぢ", "ラーーラララ"),
		new ObjectPair<String, String>("ぅ", "ラーーララー"),
		new ObjectPair<String, String>("。", "ラーーラーラ"),
		new ObjectPair<String, String>("わ", "ラーーラーー"),
		new ObjectPair<String, String>("ゐ", "ラーーーララ"),
		new ObjectPair<String, String>("ゑ", "ラーーーラー"),
		new ObjectPair<String, String>("？", "ラーーーーラ"),
		new ObjectPair<String, String>("ぱ", "ラーーーーー"),
	};

	static
	{
		pToF = new LinkedHashMap<String, String>();
		fToP = new LinkedHashMap<String, String>();
		cToI = new LinkedHashMap<Character, Boolean>();

		for(ObjectPair<String, String> obj : gentable)
		{
			String o1 = obj.getValue1();
			String o2 = obj.getValue2();
			pToF.put(o1, o2);
			fToP.put(o2, o1);
			cToI.put(Character.valueOf(o1.charAt(0)), true);
		}
	}
}
