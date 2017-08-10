package com.example.myutils.utils.CommonUtils;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
	/**
	 * Read an input stream into a string
	 */
	public final static String streamToString(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1; ) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	/**
	 * 将流另存为文件
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public final static void streamSaveAsFile(InputStream is, File outfile) {
		try{
			FileOutputStream fos = new FileOutputStream(outfile);
			byte[] buffer = new byte[1024];
			int len;
			while ((len = is.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
