package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {
    
    List<Account> find(String personKey);
    
    Account create(Account.Type type, String personKey, String bankKey);

}
