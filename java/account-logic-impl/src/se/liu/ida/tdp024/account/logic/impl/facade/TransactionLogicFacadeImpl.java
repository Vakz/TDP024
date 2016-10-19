/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLogger.AccountLoggerLevel;

/**
 *
 * @author frejo105
 */
public class TransactionLogicFacadeImpl implements TransactionLogicFacade {
    
    TransactionEntityFacade transactionEntityFacade;
    AccountLogger logger;
    
    private void log(AccountLogger.AccountLoggerLevel level, String message) {
        logger.log(level, "TransactionLogicFacade", message);
    }

    public TransactionLogicFacadeImpl(TransactionEntityFacade transactionEntityFacade, AccountLogger logger) {
        this.transactionEntityFacade = transactionEntityFacade;
        this.logger = logger;
    }

    @Override
    public String transaction(int id) {
        List<Transaction> transactions = transactionEntityFacade.transactions(id);
        log(AccountLoggerLevel.INFO, "FOUND TRANSACTIONS, total " + transactions.size() + " results");
        AccountJsonSerializer serializer = new AccountJsonSerializerImpl();
        return serializer.toJson(transactions);
    }

    @Override
    public String credit(int id, int amount) {
        return transactionEntityFacade.credit(id, amount).toString();
    }

    @Override
    public String debit(int id, int amount) {
        return transactionEntityFacade.debit(id, amount).toString();
    }
    
}
