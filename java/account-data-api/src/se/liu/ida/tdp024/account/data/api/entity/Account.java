package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Account extends Serializable {
    public void setId(int id) throws IllegalArgumentException;
    public int getId();
    
    public void setPersonKey(String id) throws IllegalArgumentException;
    public String getPersonKey();
    
    public void setType(Type type);
    public Type getType();
    
    public void setBankKey(String id) throws IllegalArgumentException;
    public String getBankKey();
    
    public void setHoldings(int id) throws IllegalArgumentException;
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
