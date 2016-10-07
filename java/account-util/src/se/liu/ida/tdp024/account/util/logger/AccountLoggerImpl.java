package se.liu.ida.tdp024.account.util.logger;

import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;


public class AccountLoggerImpl implements AccountLogger {

    private final String LOGGER_URL = "http://localhost:8090/log";
    
    @Override
    public void log(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void log(AccountLoggerLevel accountLoggerLevel, String tag, String message) {
        HTTPHelper httpHelper = new HTTPHelperImpl();
        httpHelper.get(LOGGER_URL, "tag", tag, "message", message, "level", Integer.toString(accountLoggerLevel.ordinal()));
    }    
}
