package com.caraquri.bookmanager_android.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChangeDateFormat {
    private int year;
    private int month;
    private int day;

    public String changeDateFormat(String receivedDate) {
        String date = receivedDate.substring(5, receivedDate.length() - 13);
        String dd = date.substring(0, 2);
        String MMM = date.substring(3, 6);
        String yyyy = date.substring(7, 11);

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MMM/dd", Locale.ENGLISH);

        try {
            Date mDate = format.parse(yyyy + "/" + MMM + "/" + dd);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mDate);
            this.year = calendar.get(Calendar.YEAR);
            this.month = calendar.get(Calendar.MONTH) + 1;
            this.day = calendar.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.year + "/" + this.month + "/" + this.day;
    }



}
