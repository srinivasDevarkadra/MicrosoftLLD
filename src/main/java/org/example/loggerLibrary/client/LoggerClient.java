package org.example.loggerLibrary.client;

import org.example.loggerLibrary.chain.ChainSetter;
import org.example.loggerLibrary.chain.Logger;

public class LoggerClient {


    public static void main(String[] args) {

        Logger errorLogger = ChainSetter.getChain();

        errorLogger.log(1, "this is infoLog");
        errorLogger.log(2, "this is debugLog");
        errorLogger.log(3, "this is errorLog");
    }
}
