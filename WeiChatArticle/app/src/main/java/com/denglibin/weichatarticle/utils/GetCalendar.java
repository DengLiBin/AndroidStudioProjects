package com.denglibin.weichatarticle.utils;

import java.util.Calendar;

/**
 * Created by DengLibin on 2016/5/8.
 */
public class GetCalendar {
    public static String getCalendar(){
        Calendar calendar=Calendar.getInstance();
        String dates=String.format("%d-%d-%d",calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH));
        //System.out.println(dates);
        return dates;
    }
}
