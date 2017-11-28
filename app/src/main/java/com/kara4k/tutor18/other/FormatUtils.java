package com.kara4k.tutor18.other;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormatSymbols;
import java.util.Calendar;

public class FormatUtils {

    public static final String ZERO = "0";

    public static String formatTime(int hour, int min) {
        String h = formatTimeUnit(hour);
        String m = formatTimeUnit(min);
        return String.format("%s:%s", h, m);
    }

    private static String formatTimeUnit(int i) {
        if (i < 10) return String.format("0%d", i);
        return String.valueOf(i);
    }

    public static String[] getSortedDays() {
        String[] dayNames = DateFormatSymbols.getInstance().getWeekdays();
        String[] sortedDays = new String[7];

        fillFromMonday(dayNames, sortedDays);
        return sortedDays;
    }

    public static BigDecimal formatPrice(String price) {
        BigDecimal number;
        if (price == null || price.equals("")) {
            number = new BigDecimal(ZERO);
        } else {
            number = new BigDecimal(price);
        }
        return number.setScale(2, RoundingMode.CEILING);
    }

    public static String formatPrice(double value) {
        return new BigDecimal(value)
                .setScale(2, RoundingMode.CEILING)
                .toPlainString();
    }

    private static void fillFromMonday(String[] dayNames, String[] sortedDays) {
        sortedDays[0] = dayNames[Calendar.MONDAY];
        sortedDays[1] = dayNames[Calendar.TUESDAY];
        sortedDays[2] = dayNames[Calendar.WEDNESDAY];
        sortedDays[3] = dayNames[Calendar.THURSDAY];
        sortedDays[4] = dayNames[Calendar.FRIDAY];
        sortedDays[5] = dayNames[Calendar.SATURDAY];
        sortedDays[6] = dayNames[Calendar.SUNDAY];
    }
}