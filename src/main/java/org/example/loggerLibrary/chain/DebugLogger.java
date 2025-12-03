package org.example.loggerLibrary.chain;

import org.example.loggerLibrary.appenderStrategy.AppenderStrategy;

public class DebugLogger extends Logger {
    public Logger nextLogger;

    public DebugLogger(int level, AppenderStrategy appenderStrategy) {
        super(level, appenderStrategy);
    }


    @Override
    protected void write(String message) {
        System.out.println("DEBUG Logger: " + message);
    }


}
