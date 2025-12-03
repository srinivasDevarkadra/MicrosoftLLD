package org.example.loggerLibrary.chain;

import org.example.loggerLibrary.appenderStrategy.AppenderStrategy;

public class ErrorLogger extends Logger {

    public Logger nextLogger;

    public ErrorLogger(int level, AppenderStrategy appenderStrategy) {
        super(level, appenderStrategy);
    }

    @Override
    protected void write(String message) {
        System.out.println("ERROR Logger: " + message);
    }
}
