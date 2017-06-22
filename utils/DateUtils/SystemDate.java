package com.rmyh.myutils.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 当前日期获取类
 */

public class SystemDate {


	//获取年月日
	public static String getFileName() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(new Date(System.currentTimeMillis()));
		return date;
	}

	//获取年月日时分秒
	public static String getDateEN() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = format.format(new Date(System.currentTimeMillis()));
		return date1;
	}

}