package org.learn.book_management_system.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private DateUtil(){}

    private static final DateTimeFormatter GUI_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyy-MM-dd hh:mm:ss");
    private static final DateTimeFormatter GUI_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyy-MM-dd hh:mm:ss");

    public static String forGUI(LocalDateTime dateTime){
        return GUI_DATE_TIME_FORMATTER.format(dateTime);
    }
}
