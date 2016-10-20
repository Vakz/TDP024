package se.liu.ida.tdp024.account.logic.api.facade;


public interface AccountLogicFacade {
    
    String find(String name);
    
    String create(String type, String bank, String name);
    
    
}
