/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.impl.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

/**
 *
 * @author frejo105
 */
@Entity
public class TransactionDB implements Transaction {

    public TransactionDB(int id, Type type, int amount, String created, Status status, Account account) {
        // Using setters to make sure no validation is circumvented
        // Sort of useless, since they're all simple setters, but probably good practice
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.created = created;
        this.status = status;
        this.account = account;
    }
    
    public TransactionDB() { }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Type type;

    private int amount;

    private String created;

    private Status status;

    @ManyToOne(targetEntity = AccountDB.class)
    private Account account;

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
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
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public void setCreated(String date) {
        this.created = date;
    }

    @Override
    public String getCreated() {
        return created;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public Account getAccount() {
        return account;
    }

}
