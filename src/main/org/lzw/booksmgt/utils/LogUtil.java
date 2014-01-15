package org.lzw.booksmgt.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtil {

    private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void info(String msg) {
        System.out.println("### " + SDF.format(new Date()) + " - " + msg);
    }

    public static void error(String error) {
        System.err.println("### ERROR: " + SDF.format(new Date()) + " - " + error);
    }
}
