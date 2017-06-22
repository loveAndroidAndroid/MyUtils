package com.rmyh.myutils.utils.CommonUtils;

import android.widget.Toast;

import com.rmyh.yanxun.config.RmyhApplication;

/**
 * Toast工具类
 */

public class ToastUtils {

    private static Toast toast = null;

    //这种写法可防止一个界面多次弹出toast
    public static void showShortToast(String msg) {
        if (toast == null) {
            //你自己的全局的上下文对象
            toast = Toast.makeText(Application.getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void showLongToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(Application.getContext(), msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.show();
    }
}
