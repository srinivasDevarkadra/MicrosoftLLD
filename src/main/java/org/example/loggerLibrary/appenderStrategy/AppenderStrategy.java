package org.example.loggerLibrary.appenderStrategy;

public interface AppenderStrategy {
    void append(String message);

    default void close() {
    }
}
