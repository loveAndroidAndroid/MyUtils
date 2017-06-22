package com.rmyh.myutils.utils.CommonUtils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 流操作类，用来关闭多个写入 读出流
 */

public class StreamUtils{

	public static void close(Closeable... streams){
		if(streams != null){
			for (Closeable closeable : streams) {
				if(closeable == null){
					continue;
				}
				try {
					closeable.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
