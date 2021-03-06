package se.liu.ida.tdp024.account.logic.test.facade;

import java.util.List;
import se.liu.ida.tdp024.account.logic.test.util.HTTPHelperMock;
import se.liu.ida.tdp024.account.logic.test.util.AccountEntityFacadeMock;
import se.liu.ida.tdp024.account.logic.test.util.AccountLoggerMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;

public class AccountLogicFacadeTest {

    
    //--- Unit under test ---//
    public AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeMock(), new HTTPHelperMock(), new AccountLoggerMock());
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testFind() {
        String expected = "[{\"id\":1,\"personKey\":\"personkey\",\"accountType\":\"CHECK\",\"bankKey\":\"bankkey\",\"holdings\":2}]";
        
        List<Account> accounts = accountLogicFacade.find("Emelie");
        Account first = accounts.get(0);
        Assert.assertEquals(1, first.getId()); 
        Assert.assertEquals("personkey", first.getPersonKey());
        Assert.assertEquals("bankkey", first.getBankKey());
        Assert.assertEquals(Account.Type.CHECK, first.getAccountType());
        Assert.assertEquals(2, first.getHoldings());
    }
    
    @Test
    public void testInvalidWhitespaceFind() {
        List<Account> accounts = accountLogicFacade.find("      ");
        Assert.assertTrue(accounts.isEmpty());
        
    }
    
    @Test
    public void testInvalidNullFind() {
        List<Account> accounts = accountLogicFacade.find(null);
       Assert.assertTrue(accounts.isEmpty());
    }
    
    @Test
    public void testCreateCheck() throws Exception  {
        String res = accountLogicFacade.create("CHECK", "Emelie", "Swedbank");
        Assert.assertEquals("OK", res);
    }
    
    @Test
    public void testCreateSavings() throws Exception  {
        String res = accountLogicFacade.create("SAVINGS", "Emelie", "Swedbank");
        Assert.assertEquals("OK", res);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testCreateInvalidType() throws Exception  {
        String res = accountLogicFacade.create("NOEXIST", "Emelie", "Swedbank");
    }
    
    @Test
    public void testInvalidWhitespacePersonCreate() throws Exception {
        String res = accountLogicFacade.create("CHECK", "    ", "Swedbank");
        Assert.assertEquals("FAILED", res);
    }
    
    @Test
    public void testInvalidWhitespaceBankCreate() throws Exception  {
        String res = accountLogicFacade.create("CHECK", "Emelie", "    ");
        Assert.assertEquals("FAILED", res);
    }
    
    @Test
    public void testInvalidNullPersonCreate() throws Exception  {
        String res = accountLogicFacade.create("CHECK", null, "Swedbank");
        Assert.assertEquals("FAILED", res);
    }
    
    @Test
    public void testInvalidNullBankCreate() throws Exception  {
        String res = accountLogicFacade.create("CHECK", "Emelie", null);
        Assert.assertEquals("FAILED", res);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidType() throws Exception  {
        accountLogicFacade.create("NOEXIST", "Emelie", "Swedbank");
    }
}