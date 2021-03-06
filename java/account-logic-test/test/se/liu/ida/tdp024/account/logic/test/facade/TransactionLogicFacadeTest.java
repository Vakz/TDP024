/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.test.facade;

import java.util.List;
import se.liu.ida.tdp024.account.logic.test.util.TransactionEntityFacadeMock;
import se.liu.ida.tdp024.account.logic.test.util.AccountLoggerMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;

/**
 *
 * @author frejo105
 */
public class TransactionLogicFacadeTest {
    AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB(new AccountLoggerMock());
    TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeMock(), new AccountLoggerMock());
    private static StorageFacade storageFacade = new StorageFacadeDB();
    Account account;
    
    @Before
    public void setup() throws Exception {
        account = accountEntityFacade.create(Account.Type.CHECK, "1e8a4f8a29989789cbb6726f14934f2f", "7fe7b9a7b3a9168cfbd1a2af2c58aaa6");
    }
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void findNoTransactions() {
        List<Transaction> transactions = transactionLogicFacade.transactions(-1);
        Assert.assertTrue(transactions.isEmpty());
    }
    
    @Test
    public void testValidCredit() {
        String res = transactionLogicFacade.credit(account.getId(), 5);
        Assert.assertEquals("OK", res);
    }
    
    @Test
    public void testInvalidCredit() {
        String res = transactionLogicFacade.credit(account.getId(), -5);
        Assert.assertEquals("FAILED", res);
    }
    
    @Test
    public void testValidDebit() {
        transactionLogicFacade.credit(account.getId(), 5);
        
        String res = transactionLogicFacade.credit(account.getId(), 5);
        Assert.assertEquals("OK", res);
    }
    
    @Test
    public void testInvalidDebit() {
        String res = transactionLogicFacade.credit(account.getId(), -5);
        Assert.assertEquals("FAILED", res);
    }
    
    @Test
    public void findTransaction() throws Exception {        
        String result = transactionLogicFacade.credit(account.getId(), 5);
        Assert.assertEquals("OK", result);
        
        List<Transaction> transactions = transactionLogicFacade.transactions(account.getId());
        Transaction first = transactions.get(0);
        Assert.assertEquals(account.getId(), first.getAccount().getId());
        Assert.assertEquals(5, first.getAmount());
        Assert.assertEquals(Transaction.Type.CREDIT, first.getType());
        Assert.assertEquals(Transaction.Status.OK, first.getStatus());
    }
}
