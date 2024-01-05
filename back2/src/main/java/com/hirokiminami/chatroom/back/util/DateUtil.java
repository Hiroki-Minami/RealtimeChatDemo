package com.hirokiminami.chatroom.back.util;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static Instant convertDateStr(String dateStr) throws ParseException {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateStr);
        return zonedDateTime.toInstant();
    }
}
