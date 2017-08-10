package com.example.myutils.utils.CommonUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * md5加密工具类
 */
public class MD5Utils {

	public static String encode(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			// 加密转换
			byte[] digest = md.digest(text.getBytes());
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				int a = b & 0xff;// 取低8位 取正
				String hexString = Integer.toHexString(a);
				if (hexString.length() == 1) {
					hexString = "0" + hexString;
				}
				sb.append(hexString);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
