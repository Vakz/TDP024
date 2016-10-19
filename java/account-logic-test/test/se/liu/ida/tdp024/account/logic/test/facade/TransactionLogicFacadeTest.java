/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.test.facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;

/**
 *
 * @author frejo105
 */
public class TransactionLogicFacadeTest {
    TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeMock(), new AccountLoggerMock());
    
    @Test
    public void findTransaction() {
        String transactions = transactionLogicFacade.transaction(1);
       
        Assert.assertEquals("[{\"id\":1,\"type\":\"CREDIT\",\"amount\":5,\"created\":\"2016-10-19 11:15:36\",\"status\":\"OK\",\"account\":{\"id\":1,\"personKey\":\"personkey\",\"accountType\":\"CHECK\",\"bankKey\":\"bankkey\",\"holdings\":10}}]", transactions);
    }
    
    @Test
    public void findNoTransactions() {
        String transactions = transactionLogicFacade.transaction(-1);
        Assert.assertEquals("[]", transactions);
    }
    
    @Test
    public void testValidCredit() {
        String res = transactionLogicFacade.credit(1, 5);
        Assert.assertEquals("OK", res);
    }
    
    @Test
    public void testInvalidCredit() {
        String res = transactionLogicFacade.credit(1, -5);
        Assert.assertEquals("FAILED", res);
    }
    
    @Test
    public void testValidDebit() {
        String res = transactionLogicFacade.credit(1, 5);
        Assert.assertEquals("OK", res);
    }
    
    @Test
    public void testInvalidDebit() {
        String res = transactionLogicFacade.credit(1, -5);
        Assert.assertEquals("FAILED", res);
    }
}
