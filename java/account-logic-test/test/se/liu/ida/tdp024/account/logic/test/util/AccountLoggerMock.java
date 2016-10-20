/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.test.util;

import se.liu.ida.tdp024.account.util.logger.AccountLogger;

/**
 *
 * @author frejo105
 */
public class AccountLoggerMock implements AccountLogger {

    @Override
    public void log(Throwable throwable) {
        // Do nothing
    }

    @Override
    public void log(AccountLoggerLevel todoLoggerLevel, String tag, String message) {
        System.out.println("LOGGING: " + message);
    }
    
}
