package com.Flanaria;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConnectUtils
{
	public static String connect(List<String> _list, String head, String foot)
	{
		String res = "";
		for(String str : _list)
		{
			res += head + str + foot;
		}
		return res;
	}

	public static List<long[]> splitLongArray(long[] array, int size)
	{
		List<long[]> list = new ArrayList<long[]>();
		for (int i = 0; i < array.length; i += size)
		{
			int subArrayLength = (array.length - i > size) ? size : (array.length - i);
			long[] subArray = new long[subArrayLength];
			System.arraycopy(array, i, subArray, 0, subArrayLength);
			list.add(subArray);
		}
		return list;
	}

	public static <T> List<T[]> splitArray(T[] array, int size, Class<?> arrayClass)
	{
		List<T[]> list = new ArrayList<T[]>();
		for (int i = 0; i < array.length; i += size)
		{
			int subArrayLength = (array.length - i > size) ? size : (array.length - i);
			T[] subArray = newArrayInstance(arrayClass, subArrayLength);
			System.arraycopy(array, i, subArray, 0, subArrayLength);
			list.add(subArray);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private static <T> T[] newArrayInstance(Class<?> arrayClass, int subArrayLength)
	{
		return (T[]) Array.newInstance(arrayClass, subArrayLength);
	}

	public static String connect(Set<String> cat, String head, String foot)
	{
		String res = "";
		for(String str : cat)
		{
			res += head + str + foot;
		}
		return res;
	}
}
