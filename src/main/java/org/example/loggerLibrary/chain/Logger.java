package org.example.loggerLibrary.chain;

import org.example.loggerLibrary.appenderStrategy.AppenderStrategy;

public abstract class Logger {

    public int level;

    public Logger nextLogger;

    public AppenderStrategy appenderStrategy;

    public Logger(int level, AppenderStrategy appenderStrategy) {
        this.level = level;
        this.appenderStrategy = appenderStrategy;
    }


    public void setNextLogger(Logger logger) {
        this.nextLogger = logger;

    }


    protected abstract void write(String message);


    public void log(int level, String message) {
        if (this.level <= level) {
            write(message);
            appenderStrategy.append(message);
        }
        if (this.nextLogger != null) {
            nextLogger.log(level, message);
        }
    }
}
