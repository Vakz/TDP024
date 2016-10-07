package se.liu.ida.tdp024.account.logic.api.facade;


public interface AccountLogicFacade {
    
    public String find(String name);
    
    public String create(String type, String bank, String name);
    
    
}
