package org.example.loggerLibrary.chain;

import org.example.loggerLibrary.appenderStrategy.AppenderStrategy;
import org.example.loggerLibrary.appenderStrategy.ConsoleAppender;

public class ChainSetter {


    public static Logger getChain() {
        AppenderStrategy console = new ConsoleAppender();
        //AppenderStrategy file    = new FileAppender("errors.log");
        ErrorLogger errorLogger = new ErrorLogger(3, console);
        DebugLogger debugLogger = new DebugLogger(2, console);
        InfoLogger infoLogger = new InfoLogger(1, console);

        errorLogger.setNextLogger(debugLogger);
        debugLogger.setNextLogger(infoLogger);
        infoLogger.setNextLogger(null);

        return errorLogger;
    }


}
