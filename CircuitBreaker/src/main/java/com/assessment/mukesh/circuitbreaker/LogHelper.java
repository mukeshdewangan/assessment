package com.assessment.mukesh.circuitbreaker;

public class LogHelper {
    private final org.slf4j.Logger slf4j;

    public LogHelper(org.slf4j.Logger slf4j) {
        this.slf4j = slf4j;
    }
    public static LogHelper getLogger(Class<?> clazz){
        org.slf4j.Logger slf4j = org.slf4j.LoggerFactory.getLogger(clazz);
        return new LogHelper(slf4j);
    }

    public void debug(String format, Object... args) {
        String msg = String.format(format, args);
        slf4j.debug(msg);
    }

    public void info(String format, Object... args) {
        String msg = String.format(format, args);
        slf4j.info(msg);
    }

    public void error(Throwable cause, String format, Object... args) {
        String msg = String.format(format, args);
        slf4j.error(msg, cause);
    }
}
