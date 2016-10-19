package se.liu.ida.tdp024.account.logic.test.facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
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
        String expected = "[{\"id\":1,\"personKey\":\"personkey\",\"type\":\"CHECK\",\"bankKey\":\"bankkey\",\"holdings\":2}]";
        
        String res = accountLogicFacade.find("Emelie");
        Assert.assertEquals(expected, res);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidWhitespaceFind() {
        accountLogicFacade.find("      ");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidNullFind() {
        accountLogicFacade.find(null);
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
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidWhitespacePersonCreate() throws Exception {
        accountLogicFacade.create("CHECK", "    ", "Swedbank");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidWhitespaceBankCreate() throws Exception  {
        accountLogicFacade.create("CHECK", "Emelie", "    ");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidNullPersonCreate() throws Exception  {
        accountLogicFacade.create("CHECK", null, "Swedbank");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidNullBankCreate() throws Exception  {
        accountLogicFacade.create("CHECK", "Emelie", null);
    }

    public void testInvalidType() throws Exception  {
        String res = accountLogicFacade.create("NOEXIST", "Emelie", "Swedbank");
        Assert.assertEquals("FAILED", res);
    }
}