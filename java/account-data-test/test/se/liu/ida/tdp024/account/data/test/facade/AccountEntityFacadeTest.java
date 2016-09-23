package se.liu.ida.tdp024.account.data.test.facade;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;

public class AccountEntityFacadeTest {
    
    //---- Unit under test ----//
    private AccountEntityFacade accountEntityFacade;
    private StorageFacade storageFacade;
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testCreateValid() {
        Account result = accountEntityFacade.create(Account.Type.CHECK, "Fredrik", "Swedbank");
        Assert.assertEquals("7fe7b9a7b3a9168cfbd1a2af2c58aaa6", result.getBankKey());
        Assert.assertEquals("1e8a4f8a29989789cbb6726f14934f2f", result.getPersonKey());
        Assert.assertEquals(Account.Type.CHECK, result.getAccountType());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testCreateNoName() {
        accountEntityFacade.create(Account.Type.CHECK, "", "Swedbank");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testCreateNoBank() {
        accountEntityFacade.create(Account.Type.CHECK, "Fredrik", "");
    }
    
    @Test
    public void testFindWithResult() {
        List<Account> result = accountEntityFacade.find("Fredrik");
        Assert.assertEquals("1e8a4f8a29989789cbb6726f14934f2f", result.get(0).getPersonKey());
    }
    
    @Test
    public void testFindNoResults() {
        List<Account> result = accountEntityFacade.find("Emelie");
        Assert.assertEquals(0, result.size());
    }
}