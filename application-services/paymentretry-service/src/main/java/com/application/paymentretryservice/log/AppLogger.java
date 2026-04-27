package com.application.paymentretryservice.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {
    private static AppLogger instance;
    private Logger logger;

    private AppLogger() {
        logger = LoggerFactory.getLogger(AppLogger.class);
    }

    //Thread safe singleton, double-checked locking
    public static AppLogger getInstance() {
        if(instance == null) {
            synchronized (AppLogger.class) {
                if(instance == null) {
                    instance = new AppLogger();
                }
            }
        }
        return instance;
    }

    public void info(String message) {
        logger.info(message);
    }

    public void debug(String message) {
        logger.debug(message);
    }

    public void error(String message) {
        logger.error(message);
    }
}
