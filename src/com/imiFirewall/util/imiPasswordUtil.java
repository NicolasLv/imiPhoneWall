package com.imiFirewall.util;

import android.content.Context;


public class imiPasswordUtil
{
	
	private final Context mContext;
	
	protected imiPasswordUtil(Context context)
	{
		mContext=context;
	}
	
	//���ص�ǰ���ʵ�����Խ�����ز����Ķ���
	public static imiPasswordUtil getInstance(Context context) 
	{
		return new imiPasswordUtil(context);
	}
}