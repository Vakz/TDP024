/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.test.facade;

import org.junit.After;
import org.junit.Before;
import se.liu.ida.tdp024.account.data.api.entity.Account;
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
    private StorageFacade storageFacade;
    
    @Before
    public void setup() {
        AccountEntityFacade accountEntityFacade;
        accountEntityFacade.create(Account.Type.CHECK, "Fredrik", "Swedbank");
    }
    
    @After
    public void teardown() {
        storageFacade.emptyStorage();
    }
    
}
