package com.rmyh.myutils.utils.SystemUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heima.mobilesake.bean.SmsInfo;
import com.heima.mobilesake.utils.StreamUtils;

public class SmsUtils {
	/**
	 * 定义一个备份短信的工具类方法
	 * */
	public static void backupSms(final Activity activity,
			final OnBackupListener listener) {
		// 因为是耗时的操作
		new Thread() {
			public void run() {
				// 定义一个集合存储
				ArrayList<SmsInfo> list = new ArrayList<SmsInfo>();
				// 得到一个ContentResolver对象
				ContentResolver resolver = activity.getContentResolver();
				Uri uri = Uri.parse("content://sms");
				Cursor cursor = resolver.query(uri, new String[] { "address",
						"date", "read", "type", "body" }, null, null, null);
				// 定义个变量存储进度值
				int pregress = 0;
				if (cursor != null) {
					while (cursor.moveToNext()) {
						// 得到最大值
						listener.onMax(cursor.getCount());
						String address = cursor.getString(0);
						long date = cursor.getLong(1);
						int read = cursor.getInt(2);
						int type = cursor.getInt(3);
						String body = cursor.getString(4);
						SmsInfo smsInfo = new SmsInfo(address, date, read,
								type, body);
						// 添加数据
						list.add(smsInfo);

						listener.onProgress(++pregress);
					}
					cursor.close();
				}
				// 2.把集合infos 转成json字符串
				Gson gson = new Gson();
				// 把一个集合转换为json字符串
				String json = gson.toJson(list);
				// 3.把短信存到sd卡
				// 保存的路径,并写入数据getAbsolutePath()以后使用这个方法得到绝对路径
				String path = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/sms.json";
				File file = new File(path);
				BufferedWriter bw = null;
				try {
					bw = new BufferedWriter(new FileWriter(file));
					bw.write(json);
					// 回复备份成功// 运行在ui线程 方便外部调用时更新UI
					activity.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							listener.onSuccess();
						}
					});
				} catch (final Exception e) {
					e.printStackTrace();
					// 回显备份失败的响应
					activity.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							listener.onFail(e);
						}
					});
				} finally {
					if (bw != null) {
						StreamUtils.close(bw);
					}
				}
			};
		}.start();

	}

	/**
	 * 创建一个接口提供进度条使用数据的公共方法
	 * 
	 * */
	public interface OnBackupListener {
		void onMax(int max);

		void onProgress(int progress);

		void onSuccess();// 备份成功

		void onFail(Exception e);// 备份失败
	}

	/**
	 * 定义一个还原短信的工具类，就是从json中读取出来重新写回去数据库
	 * */
	public static void restore(final Activity activity,
			final OnBackupListener listener) {
		new Thread() {
			public void run() {
				// 连接数据库
				ContentResolver resolver = activity.getContentResolver();
				Uri uri = Uri.parse("content://sms");

				// 防止还原重复的数据，每次都清除短信
				resolver.delete(uri, null, null);

				// 去除json的数据
				String path = Environment.getExternalStorageDirectory()
						.getPath() + "/sms.json";
				File file = new File(path);
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(file));
					// 2.把短信字符串转换为集合
					Gson gson = new Gson();
					// 把内容转换为bean
					// SmsInfo info = gson.fromJson(fr, SmsInfo.class);

					// 设置转换类型 这里 不用记
					Type type = new TypeToken<ArrayList<SmsInfo>>() {
					}.getType();
					ArrayList<SmsInfo> smsInfo = gson.fromJson(br, type);
					// 响应进度条最大值
					listener.onMax(smsInfo.size());
					// 写入数据
					int progress = 0;
					for (SmsInfo Info : smsInfo) {
						ContentValues values = new ContentValues();
						values.put("address", Info.address);
						values.put("date", Info.date);
						values.put("read", Info.read);
						values.put("type", Info.type);
						values.put("body", Info.body);
						resolver.insert(uri, values);
						// 响应进度条每时每刻的值
						listener.onProgress(++progress);
					}
					activity.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							listener.onSuccess();
						}
					});
				} catch (final Exception e) {
					e.printStackTrace();
					activity.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							listener.onFail(e);
						}
					});
				} finally {
					if (br != null) {
						StreamUtils.close(br);
					}
				}
			};
		}.start();
	}
}
