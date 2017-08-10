package com.example.myutils.utils.SystemUtils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.rmyh.myutils.utils.CommonUtils.StreamUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *  进程progress和内存信息操作类
 */

public class ProcessInfoProvider {
	/**
	 * 获取正在运行的Progress数量
	 * */
	public static int getRunningProgressNum(Context context) {
		// 获得活动管理器
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		// 获取正在运行的进程
		List<RunningAppProcessInfo> runningAppProcesses = am
				.getRunningAppProcesses();
		return runningAppProcesses.size();
	}

	/**
	 * 获取可有进程的数量
	 * 
	 * @param context
	 * @return
	 */
	public static int getAllProcessNum(Context context) {
		// 获取mani里面的信息
		PackageManager packageManager = context.getPackageManager();
		// 获取所有的安装包的信息
		List<PackageInfo> installedPackages = packageManager
				.getInstalledPackages(PackageManager.GET_ACTIVITIES
						| PackageManager.GET_RECEIVERS
						| PackageManager.GET_PROVIDERS
						| PackageManager.GET_SERVICES);
		// 用来存放所有的进程名子 可以去除重复
		HashSet<String> set = new HashSet<String>();
		// 遍历
		for (PackageInfo packageInfo : installedPackages) {
			// 获取应用信息的对象
			ApplicationInfo applicationInfo = packageInfo.applicationInfo;
			// 默认的进程名字 和包名一样
			String processName = applicationInfo.processName;
			set.add(processName);

			// 获取所有的activity
			ActivityInfo[] activities = packageInfo.activities;
			if (activities != null) {
				// 遍历所有的activity 获取每一个的进程名字
				for (ActivityInfo activityInfo : activities) {
					String processName1 = activityInfo.processName;
					set.add(processName1);
				}
			}
			// 获取所有的services
			ServiceInfo[] services = packageInfo.services;
			if (services != null) {
				// 遍历所有的Service 获取每一个的进程名字
				for (ServiceInfo serviceInfo : services) {
					String processName2 = serviceInfo.processName;
					set.add(processName2);
				}
			}
			// 获取所有的receivers
			ActivityInfo[] receivers = packageInfo.receivers;
			if (receivers != null) {
				for (ActivityInfo activityInfo : receivers) {
					String processName4 = activityInfo.processName;
					set.add(processName4);
				}
			}

			// 获取所有的providers
			ProviderInfo[] providers = packageInfo.providers;
			if (providers != null) {
				for (ProviderInfo providerInfo : providers) {
					String processName3 = providerInfo.processName;
					set.add(processName3);
				}
			}
		}

		return set.size();
	}

	/**
	 * 获取可用的内存的信息
	 * 
	 * @param context
	 */
	public static long getAvailMemInfo(Context context) {
		// 获得活动管理器
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo = new MemoryInfo();
		// 赋值函数 /输出函数 最终结果放到了参数里,得到内存信息
		am.getMemoryInfo(outInfo);
		// 得到可用的内存
		long availMem = outInfo.availMem;
		return availMem;
	}

	/**
	 * 获取全部的内存的信息
	 * 
	 * @param context
	 */
	@SuppressLint("NewApi")
	public static long getTotalMemInfo(Context context) {
		// 获得活动管理器
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo = new MemoryInfo();
		// 赋值函数 /输出函数 最终结果放到了参数里,得到内存信息
		am.getMemoryInfo(outInfo);
		long totalMem = 0;
		// 注意判断版本，这里的方法需要大于等于16
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			totalMem = outInfo.totalMem;
		} else {
			totalMem = getTotalMemInLow();
		}
		return totalMem;

	}

	/**
	 * 低版本获取全部内存的方法
	 * 
	 * @return
	 */
	private static long getTotalMemInLow() {
		// 读取 /proc/meminfo 文件里的第一行信息 MemTotal: 511776 kB
		String readLine = "0";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File("/proc/meminfo")));
			readLine = br.readLine();
			readLine = readLine.replaceAll("MemTotal:", "");
			readLine = readLine.replaceAll("kB", "");
			readLine = readLine.trim();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			StreamUtils.close(br);
		}

		return Long.parseLong(readLine) * 1024;// 注意单位是kb所以要乘一下
	}
	/**
	 * 得到正在运行的的进程信息
	 * */
	public static ArrayList<ProgressInfo> getProressInfo(Context context){
		ArrayList<ProgressInfo> list = new ArrayList<ProgressInfo>();
		//得到活动管理器
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);	
		//得到包管理器
		PackageManager packageManager = context.getPackageManager();
		//得到所有的进程,进程名字和进程pid
		List<RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();	
		for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
			String processName  = runningAppProcessInfo.processName;
			int pid = runningAppProcessInfo.pid;
			
			Drawable icon = null;
			String name = null;
			boolean isSys = false;
			try {
				//得到每个进程的图标，名字，是否是系统进程，占用的内存
				ApplicationInfo applicationInfo = packageManager.getApplicationInfo(processName, 0);
				icon = applicationInfo.loadIcon(packageManager);
				name = applicationInfo.loadLabel(packageManager).toString();				
				if((applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM){
					isSys = true;
				}else{
					isSys = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				//默认是进程名字
				name = processName;
				icon = context.getResources().getDrawable(R.drawable.ic_launcher);
				isSys = true;
			}
			//获取进程的内存的大小（获得是字节  所以要乘以1024）
			int[] pids = new int[]{pid};
			android.os.Debug.MemoryInfo[] processMemoryInfo = am.getProcessMemoryInfo(pids);
			long totalPss = processMemoryInfo[0].getTotalPss()*1024;
			//添加数据
			ProgressInfo info = new ProgressInfo(name, processName,icon, totalPss, isSys,false);
			list.add(info);
		}
		return list;
	}
	/**
	 * 清理选中的进程的工具类
	 * */
	public static void killProcess(Context context,String packageName){
		//得到活动管理器
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		//杀死进程
		am.killBackgroundProcesses(packageName);
	}
	/**
	 * 杀死所有进程(在关机锁屏的时候使用)
	 * @param context
	 */
	public static void killAllProcess(Context context){
		//得到所有的进程
		ArrayList<ProgressInfo> proressInfo = getProressInfo(context);
		//遍历(注意ProgressInfo info这个名字不要和集合的名字相同)
		for (ProgressInfo info : proressInfo) {
			killProcess(context, info.packageName);
		}
	}
}
