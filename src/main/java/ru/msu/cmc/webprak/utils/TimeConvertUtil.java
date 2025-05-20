package ru.msu.cmc.webprak.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeConvertUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static LocalDateTime fromString(String str ) {
        str = (str != null) ? str : "0000-00-00'T'00:00";
        return LocalDateTime.parse(str, formatter);
    }

    public static String toString(LocalDateTime time) {
        return time == null ? null :  time.format(formatter);
    }
}
