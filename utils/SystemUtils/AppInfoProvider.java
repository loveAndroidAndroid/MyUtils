package com.rmyh.myutils.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;

import com.heima.mobilesake.bean.AppInfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

/**
 * 得到所有app的信息
 * */

public class AppInfoProvider {

	//AppInfo 存储App信息的bean类
	public static ArrayList<AppInfo> getAppInfos(Context context){
		//创建一个包管理器得到所有安装包的信息
		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
		//创建一个集合存储得到appInfo
		ArrayList<AppInfo> list = new ArrayList<AppInfo>();
		
		//遍历
		for (PackageInfo packageInfo : installedPackages) {
			//得到包名
			String packageName = packageInfo.packageName;
			//得到app的信息
			ApplicationInfo applicationInfo = packageInfo.applicationInfo;
			//得到图标
			Drawable icon = applicationInfo.loadIcon(packageManager);
			//得到应用名字
			String name = applicationInfo.loadLabel(packageManager).toString();
			//创建对象
			AppInfo info = new AppInfo(name, icon, packageName);
			//添加到集合
			list.add(info);
		}
		return list;
	}
}
