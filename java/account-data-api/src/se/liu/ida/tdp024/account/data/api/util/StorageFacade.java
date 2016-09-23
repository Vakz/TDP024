package se.liu.ida.tdp024.account.data.api.util;

public interface StorageFacade {
    
    const String[] ACCOUNT_TYPES = { "DEBIT", "CHECK" };
    
    void emptyStorage();
    
}
