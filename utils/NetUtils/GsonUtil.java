package com.rmyh.myutils.utils.NetUtils;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 解析Json的封装
 *
 */
public class GsonUtil {


	/**
	 * 把一个json字符串变成对象
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> T parseJsonToBean(String json, Class<T> cls) {
		//记得关联
		Gson gson = new Gson();
		T t = null;
		try {
			t = gson.fromJson(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}


	/**
	 * 把json字符串变成集合
	 */
	public static List<?> parseJsonToList(String json, Type type) {
		Gson gson = new Gson();
		List<?> list = gson.fromJson(json, type);
		return list;
	}

	public static List<?> parseJsonToList(InputStream is, Type type) {
		Gson gson = new Gson();
		List<?> list = gson.fromJson(new InputStreamReader(is), type);
		return list;
	}

	/**
	 * 把对象变成json字符串
	 */
	public static String toJson(Object obj){
		if(obj==null)return "";
		return new Gson().toJson(obj);
	}

}
