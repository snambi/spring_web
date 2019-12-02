package com.example.demo.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static Timestamp convert(String dateStr ){

        // Wed, 27 Nov 2019 02:50:08 +0000
        String format = "EEE, dd MMM yyyy hh:mm:ss Z";
        return convert( dateStr, format);
    }

    public static Timestamp convert( String dateStr, String formatStr ){

        Timestamp result = null;

        try {

            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            Date parsedDate = format.parse(dateStr);
            result = new java.sql.Timestamp(parsedDate.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }
}
