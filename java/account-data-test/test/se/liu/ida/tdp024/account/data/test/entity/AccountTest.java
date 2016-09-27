package se.liu.ida.tdp024.account.data.test.entity;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;

public class AccountTest {
    
    //---- Unit under test ----/
    Account account = new AccountDB();
    
    @Test
    public void setValidId() {
        account.setId(10);
        Assert.assertEquals(10, account.getId());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setInvalidId() {
        account.setId(-1);
    }
    
    @Test
    public void setValidPersonKey() {
        account.setPersonKey("asd");
        Assert.assertEquals("asd", account.getPersonKey());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setInvalidPersonKey() {
        account.setPersonKey("");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setPersonKeyNull() {
        account.setPersonKey(null);
    }
    
    @Test
    public void setTypeCheck() {
        account.setType(Account.Type.CHECK);
        Assert.assertEquals(Account.Type.CHECK, account.getType());
    }
    
    @Test
    public void setValidBankKey(){
        account.setBankKey("Pantbanken");
        Assert.assertEquals("Pantbanken", account.getBankKey());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setInvalidBankKey() {
        account.setBankKey("");
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void setBankKeyNull() {
        account.setBankKey(null);
    }
    
    @Test
    public void setHoldings(){
        account.setHoldings(-1);
        Assert.assertEquals(-1, account.getHoldings());
    }
}
