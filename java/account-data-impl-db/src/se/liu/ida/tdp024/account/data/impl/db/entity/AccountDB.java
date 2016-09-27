package se.liu.ida.tdp024.account.data.impl.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import se.liu.ida.tdp024.account.data.api.entity.Account;

@Entity
public class AccountDB implements Account {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    private String personKey;
    
    private Type type;
    
    private String bankKey;
    
    private int holdings;

    @Override
    public void setId(int id) throws IllegalArgumentException {
        if (id < 0)
            throw new IllegalArgumentException("Invalid id");
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setPersonKey(String key) throws IllegalArgumentException {
        if(key == null || key.isEmpty())
            throw new IllegalArgumentException("Invalid personkey");
        this.personKey = key;
    }

    @Override
    public String getPersonKey() {
        return personKey;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setBankKey(String key) throws IllegalArgumentException {
        if (key == null || key.isEmpty())
            throw new IllegalArgumentException("Invalid bankkey");
        this.bankKey = key;
    }

    @Override
    public String getBankKey() {
        return bankKey;
    }

    @Override
    public void setHoldings(int amount) throws IllegalArgumentException {
        this.holdings = amount;
    }

    @Override
    public int getHoldings() {
        return holdings;
    }
    
}
