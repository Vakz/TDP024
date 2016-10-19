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
        String expected = "[{\"id\":1,\"personKey\":\"personkey\",\"accountType\":\"CHECK\",\"bankKey\":\"bankkey\",\"holdings\":2}]";
        
        String res = accountLogicFacade.find("Emelie");
        Assert.assertEquals(expected, res);
    }
    
    @Test
    public void testInvalidWhitespaceFind() {
        String result = accountLogicFacade.find("      ");
        Assert.assertEquals("[]", result);
        
    }
    
    @Test
    public void testInvalidNullFind() {
        String result = accountLogicFacade.find(null);
        Assert.assertEquals("[]", result);
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
        String res = accountLogicFacade.create("NOEXIST", "Emelie", "Swedbank");
    }
}