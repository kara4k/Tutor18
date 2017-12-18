package com.kara4k.tutor18.other;


import java.util.Calendar;

public class CalendarUtils {

    public static void setZeroTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public static long getDayEnd(Calendar calendar) {
        long endStamp;

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        endStamp = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return endStamp;
    }

    public static long getMonthStart(long startStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startStamp);
        CalendarUtils.setZeroTime(calendar);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTimeInMillis();
    }
}
