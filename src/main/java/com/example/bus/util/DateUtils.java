package com.example.bus.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static LocalDateTime parseDateTime(String s) {
        return LocalDateTime.parse(s);
    }

    public static String fmt(LocalDateTime dt) {
        return dt.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
    }
}