package com.rmyh.myutils.utils.CommonUtils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import java.util.Random;

/**
 * 2017/1/13 14:22
 * 随机颜色工具类
 */

public class ColorUtil {
    private static Random random = new Random();

    public static int getColor() {
        //颜色组成R G B
        //颜色表示方式一。16进制Color.parseColor("#2BABF4")
        //颜色表示方式二。10进制
        //随机颜色方案 #00000 #FFFFF
        int red = 60 + random.nextInt(146);
        int green = 60 + random.nextInt(146);
        int blue = 60 + random.nextInt(146);
        return Color.rgb(red,green,blue);
    }

    //获取多种状态的集合GradientDrawable  可以有渐变的颜色
    public static Drawable getStateDrawableList() {
        GradientDrawable background=new GradientDrawable();
        background.setColor(ColorUtil.getColor());//设置随机颜色
        background.setCornerRadius(6);//圆角

        GradientDrawable backgroundPressed=new GradientDrawable();
        backgroundPressed.setColor(ColorUtil.getColor());//设置随机颜色
        backgroundPressed.setCornerRadius(6);//圆角

        //可以设置渐变颜色的集合
        //background.setColors();


        //StateListDrawable:可以加载多张图片的集合 ，还可以给每个图片设置条件
        StateListDrawable list=new StateListDrawable();
        list.addState(new int[]{android.R.attr.state_pressed}, backgroundPressed);//添加图片并且设置条件 1.参 条件 2参 图片
        list.addState(new int[]{}, background);//添加图片并且设置条件 1.参 条件 2参 图片
        //注意默认效果图片应该最后添加
        return list;
    }
}
