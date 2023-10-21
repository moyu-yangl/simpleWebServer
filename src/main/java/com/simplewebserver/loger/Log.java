package com.simplewebserver.loger;

import java.text.SimpleDateFormat;

public class Log {
    public static final Log log;
    private static final String INFO = "[INFO]";
    private static final String ERROR = "[ERROR]";
    private static final String timeFormat = "[yyyy/MM/dd HH:mm:ss]";
    private static final SimpleDateFormat sdf = new SimpleDateFormat();

    static {
        log = new Log();
        sdf.applyPattern(timeFormat);
    }

    private Log() {
    }

    public void setTimeFormat(String format) {
        sdf.applyPattern(format);
    }

    public void info(String str, Object... strings) {
        print(str, INFO, strings);
    }

    private void print(String str, String level, Object... objects) {
        String name = Thread.currentThread().getName();
        for (Object string : objects) {
            str = str.replaceFirst("[{][}]", string.toString());
        }
        StringBuilder sb = new StringBuilder(level).append('-');
        sb.append('[').append(name).append(']').append('-')
                .append(sdf.format(System.currentTimeMillis())).append(':')
                .append(str);
        System.out.println(sb);
    }

    public void error(String str, Object... strings) {
        print(str, ERROR, strings);
    }
}
