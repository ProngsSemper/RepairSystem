package com.repairsys.util.time;

import java.text.SimpleDateFormat;


/**
 * @Author lyr
 * @create 2019/10/25 23:04
 */
public final class TimeUtil {

    public static String getCurTime()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(System.currentTimeMillis());
    }

}
