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

/**
 *
 * @author frejo105
 */
public class TransactionLogicFacadeImpl implements TransactionLogicFacade {
    
    TransactionEntityFacade transactionEntityFacade;
    AccountLogger logger;

    public TransactionLogicFacadeImpl(TransactionEntityFacade transactionEntityFacade, AccountLogger logger) {
        this.transactionEntityFacade = transactionEntityFacade;
        this.logger = logger;
    }

    @Override
    public String transaction(int id) {
        List<Transaction> transactions = transactionEntityFacade.transactions(id);
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
