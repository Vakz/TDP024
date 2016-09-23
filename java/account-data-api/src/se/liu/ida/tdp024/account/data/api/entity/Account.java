package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Account extends Serializable {
    public void setId(int id);
    public int getId();
    
    public void setPersonKey(String id);
    public String getPersonKey();
    
    public void setAccountType(Type type);
    public Type getAccountType();
    
    public void setBankKey(String id);
    public String getBankKey();
    
    public void setHoldings(int id);
    public int getHoldings();
    
    public enum Type {
        CHECK("CHECK"),
        SAVINGS("SAVINGS");
        
        private final String type;
        
        private Type(String type)
        {
            this.type = type;
        }
        
        public String getType() {
            return this.type;
        }
    };
}
