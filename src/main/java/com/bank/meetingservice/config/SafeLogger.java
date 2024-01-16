package com.bank.meetingservice.config;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.slf4j.helpers.MessageFormatter;

public class SafeLogger {

    private final Logger logger;

    private SafeLogger(Logger logger){
        this.logger=logger;
    }

    public static SafeLogger getLogger(Class<?> classz) {
        return new SafeLogger(ESAPI.getLogger(classz));
    }

    public void info(String s) {
        if (logger.isInfoEnabled()) {
            logger.info(Logger.EVENT_SUCCESS, s);
        }
    }

    public void info(String s,
                     Object... objects) {
        if (logger.isInfoEnabled()) {
            logger.info(Logger.EVENT_SUCCESS, MessageFormatter.arrayFormat(s, objects).getMessage());
        }
    }

    public void error(String var1, Object... args) {
        if (logger.isErrorEnabled()) {
            logger.error(Logger.EVENT_FAILURE, MessageFormatter.arrayFormat(var1, args).getMessage());
        }
    }

    public void error(String var1,Throwable var2, Object... args) {
        if (logger.isErrorEnabled()) {
            logger.error(Logger.EVENT_FAILURE, MessageFormatter.arrayFormat(var1, args).getMessage(),var2);
        }
    }

    public void error(String var1, String var2, Throwable var3) {
        if (logger.isErrorEnabled()) {
            logger.error(Logger.EVENT_FAILURE, MessageFormatter.format(var1, var2).getMessage(), var3);
        }
    }

    public void error(String var1, Throwable var2) {
        if (logger.isErrorEnabled()) {
            logger.error(Logger.EVENT_FAILURE, var1, var2);
        }
    }

    public void debug(String s) {
        if (logger.isInfoEnabled()) {
            logger.info(Logger.EVENT_SUCCESS, s);
        }
    }

    public void debug(String s,
                      Object... objects) {
        if (logger.isInfoEnabled()) {
            logger.info(Logger.EVENT_SUCCESS, MessageFormatter.arrayFormat(s, objects).getMessage());
        }
    }

}
