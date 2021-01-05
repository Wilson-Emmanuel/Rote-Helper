package com.wilcotech.kanjihelper.kanjihelper.utilities;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Wilson
 * on Sun, 20/12/2020.
 */
public class DateTimeUtil {
    public static String getDateTime(Instant instant){
        if(instant == null)return "";
        ZonedDateTime zd2 = instant.atZone(ZoneId.of("Asia/Tokyo"));
        //ZonedDateTime zd3 = now.atZone(ZoneId.systemDefault());
        return zd2.toString();
    }
}
