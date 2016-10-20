package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Account extends Serializable {
    void setId(int id);
    int getId();
    
    void setPersonKey(String key);
    String getPersonKey();
    
    void setAccountType(Type type);
    Type getAccountType();
    
    void setBankKey(String key);
    String getBankKey();
    
    void setHoldings(int amount);
    int getHoldings();
    
    enum Type {
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
                if (value.getType().equals(type)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Type has no corresponding value");
        }
    };
}
