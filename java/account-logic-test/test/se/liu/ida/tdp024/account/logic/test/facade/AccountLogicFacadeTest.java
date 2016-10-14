package se.liu.ida.tdp024.account.logic.test.facade;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;
import se.liu.tdp024.account.logic.impl.dto.PersonDTO;

public class AccountLogicFacadeTest {

    
    //--- Unit under test ---//
    public AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeMock(), new HTTPHelperMock());
    
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
    public void testCreate() {
        String res = accountLogicFacade.create("CHECK", "Emelie", "Swedbank");
        Assert.assertEquals("OK", res);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidWhitespacePersonCreate() {
        accountLogicFacade.create("CHECK", "    ", "Swedbank");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidWhitespaceBankCreate() {
        accountLogicFacade.create("CHECK", "Emelie", "    ");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidNullPersonCreate() {
        accountLogicFacade.create("CHECK", null, "Swedbank");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidNullBankCreate() {
        accountLogicFacade.create("CHECK", "Emelie", null);
    }

    public void testInvalidType() {
        String res = accountLogicFacade.create("NOEXIST", "Emelie", "Swedbank");
        Assert.assertEquals("FAILED", res);
    }
}