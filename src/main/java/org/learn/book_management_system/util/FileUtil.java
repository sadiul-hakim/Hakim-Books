package org.learn.book_management_system.util;

import java.io.File;

public class FileUtil {
    private FileUtil() {
    }

    private static final String BASE_PATH = "G:\\BOOK_MANAGEMENT_SYSTEM";

    public static String getFullPath(String fileName) {
        return BASE_PATH + File.separator + fileName;
    }
}
