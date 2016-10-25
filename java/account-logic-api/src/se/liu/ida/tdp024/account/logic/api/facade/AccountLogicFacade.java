package se.liu.ida.tdp024.account.logic.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;


public interface AccountLogicFacade {
    
    List<Account> find(String name);
    
    String create(String type, String bank, String name);
    
    
}
