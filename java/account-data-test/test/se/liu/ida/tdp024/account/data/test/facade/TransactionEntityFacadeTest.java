/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.test.facade;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;

/**
 *
 * @author frejo105
 */
public class TransactionEntityFacadeTest {
    
    //---- Unit under test ----//
    private TransactionEntityFacade transactionEntityFacade;
    private AccountEntityFacade accountEntityFacade;
    private StorageFacade storageFacade;
    
    //---- Variables needed for multiple tests
    int id;
    
    @Before
    public void setup() {
        
        Account account = accountEntityFacade.create(Account.Type.CHECK, "Fredrik", "Swedbank");
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
        Transaction.Status result = transactionEntityFacade.debit(id, 100);
        Assert.assertEquals(Transaction.Status.OK, result);
    }
    
    @Test
    public void testOverdraftDebit() {
        Transaction.Status result = transactionEntityFacade.debit(id, 1);
        Assert.assertEquals(Transaction.Status.FAILED, result);
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
    public void testFindTransactions() {
        List<Transaction> transactions = transactionEntityFacade.transactions(id);
        Transaction first = transactions.get(0);
        Assert.assertEquals(0, first.getId());
        Assert.assertEquals(100, first.getAmount());
        Assert.assertEquals(id, first.getAccount().getId());
        Assert.assertEquals(Transaction.Type.CREDIT, first.getType());
    }
    
    @After
    public void teardown() {
        storageFacade.emptyStorage();
    }
    
}
