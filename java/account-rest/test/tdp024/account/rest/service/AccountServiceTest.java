package tdp024.account.rest.service;

import java.util.List;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
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
        Assert.assertEquals("OK", r.getEntity());
    }
    
    @Test
    public void testCreateInvalidType() {
        Response r = accountService.create("NOEXIST", "Emelie", "Swedbank");
        Assert.assertEquals("FAILED", r.getEntity());
    }
    
    @Test
    public void testCreateInvalidBank() {
        Response r = accountService.create("CHECK", "Emelie", "NOEXIST");
        Assert.assertEquals("FAILED", r.getEntity());
    }
    
    @Test
    public void testCreateInvalidPerson() {
        Response r = accountService.create("CHECK", "NOEXIST", "Swedbank");
        Assert.assertEquals("FAILED", r.getEntity());
    }
    
    @Test
    public void testValidCredit() {
        accountService.create("CHECK", "Emelie", "Swedbank");
        
        Response r = accountService.credit(1, 20);
        Assert.assertEquals("OK", r.getEntity());
    }
    
    @Test
    public void testInvalidCredit() {
        accountService.create("CHECK", "Emelie", "Swedbank");
        
        Response r = accountService.credit(1, -10);
        Assert.assertEquals("FAILED", r.getEntity());
    }
    
    @Test
    public void testValidDebit() {
        accountService.create("CHECK", "Emelie", "Swedbank");
        accountService.credit(1, 20);
        
        Response r = accountService.debit(1, 10);
        Assert.assertEquals("OK", r.getEntity());
    }
    
    @Test
    public void testInvalidDebit() {
        accountService.create("CHECK", "Emelie", "Swedbank");        
        Response r = accountService.debit(1, -10);
        Assert.assertEquals("FAILED", r.getEntity());
    }
    
    @Test
    public void testOverdraftDebit() {
        accountService.create("CHECK", "Emelie", "Swedbank");   
        accountService.credit(1, 10);
        
        Response r = accountService.debit(1, 20);
        Assert.assertEquals("FAILED", r.getEntity());
    }
    
    @Test
    public void testMultipleTransactionsOverdraft() {
        accountService.create("CHECK", "Emelie", "Swedbank");
        Assert.assertEquals("OK", accountService.credit(1, 100).getEntity());
        Assert.assertEquals("OK", accountService.debit(1, 50).getEntity());
        Assert.assertEquals("OK", accountService.debit(1, 50).getEntity());
        Assert.assertEquals("FAILED", accountService.debit(1, 1).getEntity());
        
    }
    
    @Test
    public void testFindTransactions() {
        accountService.create("CHECK", "Emelie", "Swedbank");   
        accountService.credit(1, 10);
        
        Response r = accountService.transactions(1);
        List<Transaction> transactions = (List<Transaction>)r.getEntity();
        
        Assert.assertTrue(!transactions.isEmpty());
        Assert.assertEquals(10, transactions.get(0).getAmount());
        Assert.assertEquals(Transaction.Status.OK, transactions.get(0).getStatus());
        Assert.assertEquals(200, r.getStatus());
    }
    
    @Test
    public void testFindNoTransactions() {
        Response r = accountService.transactions(0);
        List<Transaction> transactions = (List<Transaction>)r.getEntity();
        
        Assert.assertTrue(transactions.isEmpty());
    }
    
    @Test
    public void testFindAccounts() {
        //final String expected = "[{\"id\":1,\"personKey\":\"77f76600dc6b02d307710dd4e7a0e61b\",\"accountType\":\"CHECK\",\"bankKey\":\"7fe7b9a7b3a9168cfbd1a2af2c58aaa6\",\"holdings\":0}]";
        accountService.create("CHECK", "Emelie", "Swedbank"); 
        
        Response r = accountService.findAccounts("Emelie");
        List<Account> accounts = (List<Account>)r.getEntity();
        
        Assert.assertEquals(1, accounts.size());
        Assert.assertEquals("77f76600dc6b02d307710dd4e7a0e61b", accounts.get(0).getPersonKey());
        Assert.assertEquals(Account.Type.CHECK, accounts.get(0).getAccountType());
    }
    
    @Test
    public void testFindNoAccounts() {
        Response r = accountService.findAccounts("Emelie");
        List<Account> accounts = (List<Account>)r.getEntity();
        Assert.assertEquals(200, r.getStatus());
        Assert.assertTrue(accounts.isEmpty());
    }
    
    @Test
    public void testFindNoPerson() {
        Response r = accountService.findAccounts("NOEXIST");
        List<Account> accounts = (List<Account>)r.getEntity();
        Assert.assertEquals(200, r.getStatus());
        Assert.assertTrue(accounts.isEmpty());
    }
}