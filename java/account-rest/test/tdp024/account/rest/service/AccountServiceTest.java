package tdp024.account.rest.service;

import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.rest.service.AccountService;

public class AccountServiceTest {

    //-- Units under test ---//
    private StorageFacade storageFacade = new StorageFacadeDB();
    AccountService accountService = new AccountService();

    @After
    public void tearDown() {
        storageFacade.emptyStorage();

    }

    @Test
    public void testCreate() {
        Response r = accountService.create("CHECK", "Emelie", "Swedbank");
        Assert.assertEquals(200, r.getStatus());
    }
    
    @Test
    public void testCreateInvalidType() {
        Response r = accountService.create("NOEXIST", "Emelie", "Swedbank");
        Assert.assertEquals(400, r.getStatus());
    }
    
    @Test
    public void testCreateInvalidBank() {
        Response r = accountService.create("CHECK", "Emelie", "NOEXIST");
        Assert.assertEquals(400, r.getStatus());
    }
    
    @Test
    public void testCreateInvalidPerson() {
        Response r = accountService.create("CHECK", "NOEXIST", "Swedbank");
        Assert.assertEquals(400, r.getStatus());
    }
    
    @Test
    public void testValidCredit() {
        accountService.create("CHECK", "Emelie", "Swedbank");
        
        Response r = accountService.credit(1, 20);
        Assert.assertEquals(200, r.getStatus());
    }
    
    @Test
    public void testInvalidCredit() {
        accountService.create("CHECK", "Emelie", "Swedbank");
        
        Response r = accountService.credit(1, -10);
        Assert.assertEquals(400, r.getStatus());
    }
    
    @Test
    public void testValidDebit() {
        accountService.create("CHECK", "Emelie", "Swedbank");
        accountService.credit(1, 20);
        
        Response r = accountService.debit(1, 10);
        Assert.assertEquals(200, r.getStatus());
    }
    
    @Test
    public void testInvalidDebit() {
        accountService.create("CHECK", "Emelie", "Swedbank");        
        Response r = accountService.debit(1, -10);
        Assert.assertEquals(400, r.getStatus());
    }
    
    @Test
    public void testOverdraftDebit() {
        accountService.create("CHECK", "Emelie", "Swedbank");   
        accountService.credit(1, 10);
        
        Response r = accountService.debit(1, 20);
        Assert.assertEquals(400, r.getStatus());
    }
    @Test
    public void testFindTransactions() {
        final String expected = "[{\"id\":1,\"type\":\"CREDIT\",\"amount\":10,\"date\":\"2016-10-19 00:00:00\",\"status\":\"OK\",\"account\":{\"id\":1,\"personKey\":\"77f76600dc6b02d307710dd4e7a0e61b\",\"type\":\"CHECK\",\"bankKey\":\"7fe7b9a7b3a9168cfbd1a2af2c58aaa6\",\"holdings\":10}}]";
        accountService.create("CHECK", "Emelie", "Swedbank");   
        accountService.credit(1, 10);
        
        Response r = accountService.transactions(1);
        Assert.assertEquals(200, r.getStatus());
        Assert.assertEquals(expected, r.getEntity());
    }
    
    @Test
    public void testFindNoTransactions() {
        Response r = accountService.transactions(0);
        Assert.assertEquals(200, r.getStatus());
        Assert.assertEquals("[]", r.getEntity());
    }
    
    @Test
    public void testFindAccounts() {
        final String expected = "[{\"id\":1,\"personKey\":\"77f76600dc6b02d307710dd4e7a0e61b\",\"type\":\"CHECK\",\"bankKey\":\"7fe7b9a7b3a9168cfbd1a2af2c58aaa6\",\"holdings\":0}]";
        accountService.create("CHECK", "Emelie", "Swedbank"); 
        
        Response r = accountService.findAccounts("Emelie");
        Assert.assertEquals(200, r.getStatus());
        Assert.assertEquals(expected, r.getEntity());
    }
    
    @Test
    public void testFindNoAccounts() {
        Response r = accountService.findAccounts("Emelie");
        Assert.assertEquals(200, r.getStatus());
        Assert.assertEquals("[]", r.getEntity());
    }
    
    @Test
    public void testFindNoPerson() {
        Response r = accountService.findAccounts("NOEXIST");
        Assert.assertEquals(400, r.getStatus());
    }
}