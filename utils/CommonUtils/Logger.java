package com.rmyh.myutils.utils.CommonUtils;

import android.util.Log;

/**
 * 日志工具类
 */
public class Logger {

	//如果不在使用改为false
	public static boolean showLog = true;

	/**
	 * Log.i(TAG,o.toString())
	 * @param objTag 可以是任意的对象，如果String，则直接使用作为Tag，否则的话使用类名
	 * @param objMsg 可以是任意对象，如果是String，则直接使用作为打印的字符串，否则打印它的toString
	 */
	public static void i(Object objTag, Object objMsg) {
		if (!showLog) {
			return;
		}
		
		String tag;
		String msg;
		
		if (objTag instanceof String) {
			tag = (String) objTag;
		}else {
			//获取当前实例的类的简单类名,给开发者省去Tag的开发。
			tag = objTag.getClass().getSimpleName();
		}
		
		msg = (objMsg == null || objMsg.toString() == null) ? "null" : objMsg.toString();
		
		Log.i(tag, msg);
	}
}
