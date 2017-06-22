package com.rmyh.myutils.utils.CommonUtils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by wen on 2017/6/22.
 * 键盘操作类
 */

public class InputTools {
    //隐藏虚拟键盘
    public static void HideKeyboard(View v)
    {
        InputMethodManager imm = (InputMethodManager) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
        if ( imm.isActive( ) ) {
            imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );

        }
    }

    //显示虚拟键盘
    public static void ShowKeyboard(View v)
    {
        InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );

        imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);

    }
}
