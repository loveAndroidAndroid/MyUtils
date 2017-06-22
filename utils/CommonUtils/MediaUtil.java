package com.rmyh.myutils.utils.CommonUtils;

import java.io.File;
import java.lang.reflect.Method;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

/**
 * 视频操作类
 */

public class MediaUtil {
	
	public final static String PCM_FILE_SUFFIX = ".pcm";
	public final static String MP4_FILE_SUFFIX = ".mp4";

	/**
	 * 截取视频第一帧
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	public static Bitmap getVideoFirstFrame(Context context, Uri uri) {
		Bitmap bitmap = null;
		String className = "android.media.MediaMetadataRetriever";
		Object objectMediaMetadataRetriever = null;
		Method release = null;
		try {
			objectMediaMetadataRetriever = Class.forName(className).newInstance();
			Method setDataSourceMethod = Class.forName(className).getMethod("setDataSource", Context.class, Uri.class);
			setDataSourceMethod.invoke(objectMediaMetadataRetriever, context, uri);
			Method getFrameAtTimeMethod = Class.forName(className).getMethod("getFrameAtTime");
			bitmap = (Bitmap) getFrameAtTimeMethod.invoke(objectMediaMetadataRetriever);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (release != null) {
					release.invoke(objectMediaMetadataRetriever);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}
}
