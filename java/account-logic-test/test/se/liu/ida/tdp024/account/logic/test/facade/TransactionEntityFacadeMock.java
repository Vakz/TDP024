/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.test.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;

/**
 *
 * @author frejo105
 */
public class TransactionEntityFacadeMock implements TransactionEntityFacade {
    
    public TransactionEntityFacadeMock() {
        
    }

    @Override
    public List<Transaction> transactions(int id) {
        
        if (id != 1) {
            return new ArrayList<Transaction>();
        }
        
        Account a = new AccountDB();
        Transaction t = new TransactionDB();
        a.setId(1);
        a.setHoldings(10);
        a.setPersonKey("personkey");
        a.setBankKey("bankkey");
        a.setAccountType(Account.Type.CHECK);
        t.setAccount(a);
        t.setId(1);
        t.setAmount(5);
        t.setType(Transaction.Type.CREDIT);
        t.setStatus(Transaction.Status.OK);
        Date d = new Date();
        d.setHours(11);
        d.setMonth(9);
        d.setYear(2016 - 1900);
        d.setDate(19);
        d.setMinutes(15);
        d.setSeconds(36);
        t.setCreated(d);
        ArrayList<Transaction> tl = new ArrayList<Transaction>();
        tl.add(t);
        return tl;
    }

    @Override
    public Transaction.Status credit(int id, int amount) {
        if (amount <= 0) {
            return Transaction.Status.FAILED;
        }
        return Transaction.Status.OK;
    }

    @Override
    public Transaction.Status debit(int id, int amount) {
        if (amount <= 0) {
            return Transaction.Status.FAILED;
        }
        return Transaction.Status.OK;
    }
    
}
