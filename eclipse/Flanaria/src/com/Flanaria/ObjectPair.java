package com.Flanaria;

public class ObjectPair<T1, T2>
{
	private T1 _obj1;
	private T2 _obj2;

	public ObjectPair(T1 obj1, T2 obj2)
	{
		_obj1 = obj1;
		_obj2 = obj2;
	}

	public T1 getValue1()
	{
		return _obj1;
	}

	public T2 getValue2()
	{
		return _obj2;
	}
}
