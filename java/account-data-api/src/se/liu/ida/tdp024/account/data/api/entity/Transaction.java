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

    public void setCreated(Date date);

    public Date getCreated();

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
        }

        ;
        
        public String getType() {
            return this.type;
        }

        public Type fromString(String type) {
            for (Type value : Type.values()) {
                if (value.getType().equals(type)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Type has no corresponding value");
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

        public Status fromString(String status) {
            for (Status value : Status.values()) {
                if (value.getStatus().equals(status)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Status has no corresponding value");
        }
    }

}
