/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.test.util;

import java.util.ArrayList;
import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;

/**
 *
 * @author frejo105
 */
public class AccountEntityFacadeMock implements AccountEntityFacade {

    @Override
    public List<Account> find(String personKey) {
        Account account = new AccountDB();
        account.setBankKey("bankkey");
        account.setPersonKey(personKey);
        account.setHoldings(2);
        account.setId(1);
        account.setAccountType(Account.Type.CHECK);
        
        List<Account> l = new ArrayList<Account>();
        l.add(account);
        return l;
    }

    @Override
    public Account create(Account.Type type, String personKey, String bankKey) {
        Account account = new AccountDB();
        account.setBankKey(bankKey);
        account.setPersonKey(personKey);
        account.setHoldings(2);
        account.setId(1);
        account.setAccountType(type);
        return account;
    }
    
}
