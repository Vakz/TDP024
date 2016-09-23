package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Account extends Serializable {
    public void setId(int id);
    public int getId();
    
    public void setPersonKey(int id);
    public int getPersonKey();
    
    public void setAccountType(String type);
    public String getAccountType();
    
    public void setBankKey(int id);
    public int getBankKey();
    
    public void setHoldings(int id);
    public int getHoldings();
}
