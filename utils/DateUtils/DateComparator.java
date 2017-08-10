package com.example.myutils.utils.DateUtils;

import com.rmyh.myutils.model.TimeData;
import java.util.Comparator;

/**
 * 时间比较工具类
 * 2017/5/1 10:58
 */

public class DateComparator implements Comparator<TimeData> {
    @Override
    public int compare(TimeData o1, TimeData o2) {
        return o2.getData().compareTo(o1.getData());
    }
}
