package com.caraquri.bookmanager_android.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChangeDateFormat {
    private static final String API_DATE_FORMAT = "yyyy/MMM/dd";
    private static final String BETWEEN_DATE_BACKSLASH = "/";
    private int year;
    private int month;
    private int day;

    public String fromGMTFormatToDateFormat(String receivedDate) {
        String date = receivedDate.substring(5, receivedDate.length() - 13);
        String dd = date.substring(0, 2);
        String MMM = date.substring(3, 6);
        String yyyy = date.substring(7, 11);

        SimpleDateFormat format = new SimpleDateFormat(API_DATE_FORMAT, Locale.ENGLISH);

        try {
            Date mDate = format.parse(yyyy + BETWEEN_DATE_BACKSLASH + MMM + BETWEEN_DATE_BACKSLASH + dd);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mDate);
            this.year = calendar.get(Calendar.YEAR);
            this.month = calendar.get(Calendar.MONTH) + 1;
            this.day = calendar.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.year + BETWEEN_DATE_BACKSLASH + this.month + BETWEEN_DATE_BACKSLASH + this.day;
    }
}
