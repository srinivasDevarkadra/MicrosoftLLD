package org.example.loggerLibrary.chain;

import org.example.loggerLibrary.appenderStrategy.AppenderStrategy;

public class InfoLogger extends Logger {
    public Logger nextLogger;

    public InfoLogger(int level, AppenderStrategy appender) {
        super(level, appender);
    }

    @Override
    protected void write(String message) {
        System.out.println("INFO Logger: " + message);
    }


}
