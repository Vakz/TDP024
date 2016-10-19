package se.liu.ida.tdp024.account.data.test.facade;

import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerImpl;

public class AccountEntityFacadeTest {
    
    //---- Unit under test ----//
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB(new AccountLoggerImpl());
    private static StorageFacade storageFacade = new StorageFacadeDB();
    
    @AfterClass
    public static void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testCreateValid() throws Exception {
        Account result = accountEntityFacade.create(Account.Type.CHECK, "1e8a4f8a29989789cbb6726f14934f2f", "7fe7b9a7b3a9168cfbd1a2af2c58aaa6");
        Assert.assertEquals("7fe7b9a7b3a9168cfbd1a2af2c58aaa6", result.getBankKey());
        Assert.assertEquals("1e8a4f8a29989789cbb6726f14934f2f", result.getPersonKey());
        Assert.assertEquals(Account.Type.CHECK, result.getAccountType());
        
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testCreateNoName() throws Exception {
        accountEntityFacade.create(Account.Type.CHECK, "", "7fe7b9a7b3a9168cfbd1a2af2c58aaa6");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testCreateNoBank() throws Exception {
        accountEntityFacade.create(Account.Type.CHECK, "1e8a4f8a29989789cbb6726f14934f2f", "");
        
    }
    
    @Test
    public void testFindWithResult() throws Exception {
        accountEntityFacade.create(Account.Type.CHECK, "1e8a4f8a29989789cbb6726f14934f2f", "7fe7b9a7b3a9168cfbd1a2af2c58aaa6");
        List<Account> result = accountEntityFacade.find("1e8a4f8a29989789cbb6726f14934f2f");
        Assert.assertEquals("1e8a4f8a29989789cbb6726f14934f2f", result.get(0).getPersonKey());
    }
    
    @Test
    public void testFindNoResults() {
        List<Account> result = accountEntityFacade.find("Emelie");
        Assert.assertEquals(0, result.size());
    }
}