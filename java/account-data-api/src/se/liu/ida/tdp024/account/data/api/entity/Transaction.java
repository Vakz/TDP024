/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.api.entity;

import java.util.Date;

/**
 *
 * @author frejo105
 */
public interface Transaction {
    public void setId(int id);
    public int getId();
    
    public void setType(Type type);
    public Type getType();
    
    public void setAmount(int amount);
    public int getAmount();
    
    public void setDate(Date date);
    public Date getDate();
    
    public void setStatus(Status status);
    public Status getStatus();
    
    public void setAccount(Account account);
    public Account getAccount();
    
    public enum Type {
        DEBIT("DEBIT"),
        CREDIT("CREDIT");
        
        private final String type;
        
        private Type(String type) {
            this.type = type;
        };
        
        public String getType() {
            return this.type;
        }
    }
    
    public enum Status {
        OK("OK"),
        FAILED("FAILED");
        
        private final String status;
        
        private Status(String status) {
            this.status = status;
        }
        
        public String getStatus() {
            return this.status;
        }
    }
      
}
