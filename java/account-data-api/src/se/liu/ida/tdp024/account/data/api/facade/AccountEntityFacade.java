package se.liu.ida.tdp024.account.data.api.facade;

public interface AccountEntityFacade {
    public boolean credit(int id, int amount);
    
    public boolean debit(int id, int amount);
}
