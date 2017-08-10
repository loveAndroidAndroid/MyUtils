package com.example.myutils.utils.SystemUtils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

/**
 * 判断服务是否开启
 * */

public class ServiceStateUtils {

	public static boolean idRunning(Context context,
			Class<? extends Service> serviceClass) {
		// 活动管理器 可以理解为 任务管理器
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		// 参 1 获取的最大数量（我们的程序并没多少个，设置一个大点的值就可以了）
		// 获取正在运行的服务
		List<RunningServiceInfo> runningServices = am.getRunningServices(1000);
		for (RunningServiceInfo runningServiceInfo : runningServices) {
			// 组件名称对象 当前这个对象代表 当前的service
			ComponentName serviceName = runningServiceInfo.service;
			// 获取当前服务的类名
			String className = serviceName.getClassName();
			// System.out.println("className=" + className);
			// 获取传入进来的service的类名
			String myServiceName = serviceClass.getName();
			//进行比较
			if(TextUtils.equals(className, myServiceName)){
				return true;
			}			
		}
		return false;
	}
}
