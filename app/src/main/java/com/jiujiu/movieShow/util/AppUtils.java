package com.jiujiu.movieShow.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AppUtils {

    public static int getYearFromString(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            sdf.parse(dateStr);
            return sdf.getCalendar().get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
