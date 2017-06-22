package com.rmyh.myutils.utils.CommonUtils;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by wen on 2017-1-16.
 * 仿应用市场伸缩空间工具类
 */

public class CollapseExpandUtil {
    //工具类作用是将一个LinearLayout进行折叠，再展开，配置箭头进行显示
    public static void setAnim(final LinearLayout expandView, final ImageView arrowView) {
        //步骤一。获取最大高度与最小高度
        //1.1.查找控件
        //1.2.进行测量  view.measure(0,0)
        expandView.measure(0, 0);
        //1.3获取测量高宽
        final int maxHeight = expandView.getMeasuredHeight();
        //1.4.设置最小高度
        final int miniHeight = 0;
        //步骤二。设置显示最小高度(合并状态)
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, miniHeight);
        expandView.setLayoutParams(params);
        //步骤三。编写伸(maxHeight)缩(minHeight)逻辑
        arrowView.setOnClickListener(new View.OnClickListener() {
            boolean isExpand = false;
            int startValue = 0;
            int endValue = 0;

            @Override
            public void onClick(View v) {
                if (!isExpand) {
                    isExpand = true;//展开（miniHeight->MaxHeight）
                    startValue = miniHeight;
                    endValue = maxHeight;
                    arrowView.setImageResource(R.drawable.arrow_up);
                } else {
                    isExpand = false;//收缩（MaxHeight->miniHeight）
                    startValue = maxHeight;
                    endValue = miniHeight;
                    arrowView.setImageResource(R.drawable.arrow_down);
                }
                //动画效果:定时修改高度产生的效果
                ValueAnimator valueAnimator = ValueAnimator.ofInt(startValue, endValue);
                //设置监听器
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    //只要有变化值
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        //调用get获取动态值
                        int newHeight = (int) animation.getAnimatedValue();
                        int width = LinearLayout.LayoutParams.MATCH_PARENT;
                        //重新给布局设置高度
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, newHeight);
                        expandView.setLayoutParams(params);
                    }
                });
                //设置时长
                valueAnimator.setDuration(1000);
                //开启动画
                valueAnimator.start();
            }
        });
    }
}
