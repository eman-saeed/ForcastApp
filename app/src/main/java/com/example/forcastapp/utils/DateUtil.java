package com.example.forcastapp.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String getFormatDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date newDate = new Date();
        try {
            newDate = format.parse(dateString);
            format = new SimpleDateFormat("MMM dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return format.format(newDate);
    }
}
