package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {
    
    public List<Account> find(String personKey);
    
    public Account create(Account.Type type, String personKey, String bankKey) throws IllegalArgumentException, Exception;
    
    
}
