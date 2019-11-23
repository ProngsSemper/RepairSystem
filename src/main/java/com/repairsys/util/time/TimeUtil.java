package com.repairsys.util.time;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * @Author lyr
 * @create 2019/10/25 23:04
 */
public final class TimeUtil {

    /**
     * @return 返回当前天数
     * replaced by <code>
     * TimeUtil.getNowDate
     * </code>
     * @see TimeUtil
     */
    @Deprecated
    public static String getCurTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(System.currentTimeMillis());
    }

    public static String getTime(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        java.util.Date time = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String format = df.format(time);
        return format;
    }

    public static String getTime(int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month - 1);
        java.util.Date time = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String format = df.format(time);
        return format;

    }

    public static String getNowDate() {
        LocalDate nowDay = LocalDate.now();
        return nowDay.toString();
    }

}
