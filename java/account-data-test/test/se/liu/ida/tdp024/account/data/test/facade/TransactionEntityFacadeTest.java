/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.test.facade;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerImpl;

/**
 *
 * @author frejo105
 */
public class TransactionEntityFacadeTest {
    
    //---- Unit under test ----//
    private static TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB(new AccountLoggerImpl());
    private static AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB(new AccountLoggerImpl());
    private static StorageFacade storageFacade = new StorageFacadeDB();
    
    //---- Variables needed for multiple tests
    private static int id;
    
    @Before
    public void setup() throws Exception {
        Account account = accountEntityFacade.create(Account.Type.CHECK, "1e8a4f8a29989789cbb6726f14934f2f", "7fe7b9a7b3a9168cfbd1a2af2c58aaa6");
        
        id = account.getId();
    }
   
    
    @Test
    public void testValidCredit() {
        Transaction.Status result = transactionEntityFacade.credit(id, 100);
        Assert.assertEquals(Transaction.Status.OK, result);
    }
    
    @Test
    public void testInvalidCredit() {
        Transaction.Status result = transactionEntityFacade.credit(id, -100);
        Assert.assertEquals(Transaction.Status.FAILED, result);
    }
    
    @Test
    public void testZeroCredit() {
        Transaction.Status result = transactionEntityFacade.credit(id, 0);
        Assert.assertEquals(Transaction.Status.FAILED, result);
    }
    
    @Test
    public void testValidDebit() {
        transactionEntityFacade.credit(id, 100);
        Transaction.Status result = transactionEntityFacade.debit(id, 100);
        Assert.assertEquals(Transaction.Status.OK, result);
    }
    
    @Test
    public void testOverdraftDebit() {
        Transaction.Status result = transactionEntityFacade.debit(id, 10);
        Assert.assertEquals(Transaction.Status.FAILED, result);
    }
    
    @Test
    public void testMultipleTransactionsOverdraft() {
        Assert.assertEquals(Transaction.Status.OK, transactionEntityFacade.credit(id, 100));
        Assert.assertEquals(Transaction.Status.OK, transactionEntityFacade.debit(id, 50));
        Assert.assertEquals(Transaction.Status.OK, transactionEntityFacade.debit(id, 50));
        Assert.assertEquals(Transaction.Status.FAILED, transactionEntityFacade.debit(id, 1));
    }
    
    @Test
    public void testInvalidDebit() {
        Transaction.Status result = transactionEntityFacade.debit(id, -1);
        Assert.assertEquals(Transaction.Status.FAILED, result);
    }
    
    @Test
    public void testZeroDebit() {
        Transaction.Status result = transactionEntityFacade.debit(id, 0);
        Assert.assertEquals(Transaction.Status.FAILED, result);
    }
    
    @Test
    public void testFindTransactions() throws Exception {
        transactionEntityFacade.credit(id, 100);
        
        List<Transaction> transactions = transactionEntityFacade.transactions(id);
        Transaction first = transactions.get(0);
        Assert.assertEquals(100, first.getAmount());
        Assert.assertEquals(id, first.getAccount().getId());
        Assert.assertEquals(Transaction.Type.CREDIT, first.getType());
    }
    
    @Test
    public void testFindFailedTransactions() throws Exception {
        transactionEntityFacade.debit(id, 100);
        
        List<Transaction> transactions = transactionEntityFacade.transactions(id);
        Transaction first = transactions.get(0);
        Assert.assertEquals(100, first.getAmount());
        Assert.assertEquals(id, first.getAccount().getId());
        Assert.assertEquals(Transaction.Type.DEBIT, first.getType());
        Assert.assertEquals(Transaction.Status.FAILED, first.getStatus());
    }
    
    @AfterClass
    public static void teardown() {
        storageFacade.emptyStorage();
    }
    
}
