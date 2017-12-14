package com.kara4k.tutor18.other;


import android.util.SparseIntArray;

import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.model.Lesson;
import com.kara4k.tutor18.model.Person;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormatSymbols;
import java.util.Calendar;

public class FormatUtils {

    private static final String ZERO = "0";

    private static SparseIntArray sDayDbValues;

    static {
        initDayDbValues();
    }

    public static String formatTime(int hour, int min) {
        String h = formatCalUnit(hour);
        String m = formatCalUnit(min);
        return String.format("%s:%s", h, m);
    }

    private static String formatCalUnit(int i) {
        if (i < 10) return String.format("0%d", i);
        return String.valueOf(i);
    }

    public static String[] getSortedDays() {
        String[] dayNames = DateFormatSymbols.getInstance().getWeekdays();
        String[] sortedDays = new String[7];

        fillFromMonday(dayNames, sortedDays);
        return sortedDays;
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

    public static String formatTime(Event event){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(event.getId());

        int startHour = calendar.get(Calendar.HOUR_OF_DAY);
        int startMin = calendar.get(Calendar.MINUTE);
        String startTime = formatTime(startHour, startMin);

        int duration = event.getLesson().getDuration();
        calendar.add(Calendar.MINUTE, duration);

        int endHour = calendar.get(Calendar.HOUR_OF_DAY);
        int endMin = calendar.get(Calendar.MINUTE);
        String endTime = formatTime(endHour, endMin);

        return String.format("%s - %s", startTime, endTime);
    }

    public static String formatTime(Lesson lesson) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, lesson.getStartHour());
        calendar.set(Calendar.MINUTE, lesson.getStartMin());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, lesson.getDuration());

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String start = formatTime(lesson.getStartHour(), lesson.getStartMin());
        String end = formatTime(hour, minute);

        return String.format("%s - %s", start, end);
    }

    public static String formatCalDateTime(Calendar calendar) {
        String date = formatCalDay(calendar);
        String time = formatCalTime(calendar);
        return String.format("%s - %s", date, time);
    }

    public static String formatCalDay(Calendar calendar) {
        String day = formatCalUnit(calendar.get(Calendar.DAY_OF_MONTH));
        String month = formatCalUnit(calendar.get(Calendar.MONTH) + 1);
        int year = calendar.get(Calendar.YEAR);

        return String.format("%s.%s.%d", day, month, year);
    }

    public static String formatCalTime(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        return formatTime(hour, min);
    }

    public static String formatName(Person person) {
        String firstName = person.getFirstName();
        String name = person.getName();

        return String.format("%s %s", firstName, name);
    }

    public static int getDayDbValue(int i) {
        return sDayDbValues.get(i);
    }

    private static void initDayDbValues() {
        sDayDbValues = new SparseIntArray(7);
        sDayDbValues.put(Calendar.MONDAY, 0);
        sDayDbValues.put(Calendar.TUESDAY, 1);
        sDayDbValues.put(Calendar.WEDNESDAY, 2);
        sDayDbValues.put(Calendar.THURSDAY, 3);
        sDayDbValues.put(Calendar.FRIDAY, 4);
        sDayDbValues.put(Calendar.SATURDAY, 5);
        sDayDbValues.put(Calendar.SUNDAY, 6);
    }
}
