package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Account extends Serializable {
    public void setId(int id) throws IllegalArgumentException;
    public int getId();
    
    public void setPersonKey(String key) throws IllegalArgumentException;
    public String getPersonKey();
    
    public void setAccountType(Type type);
    public Type getAccountType();
    
    public void setBankKey(String key) throws IllegalArgumentException;
    public String getBankKey();
    
    public void setHoldings(int amount) throws IllegalArgumentException;
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
        
        public static Type fromString(String type) {
            for (Type value : Type.values()) {
                if (value.getType().equals(type))
                    return value;
            }
            throw new IllegalArgumentException("Type has no corresponding value");
        }
    };
}
