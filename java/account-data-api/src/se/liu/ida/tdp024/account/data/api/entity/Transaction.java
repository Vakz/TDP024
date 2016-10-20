/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.api.entity;

/**
 *
 * @author frejo105
 */
public interface Transaction {

    void setId(int id);

    int getId();

    void setType(Type type);

    Type getType();

    void setAmount(int amount);

    int getAmount();

    void setCreated(String date);

    String getCreated();

    void setStatus(Status status);

    Status getStatus();

    void setAccount(Account account);

    Account getAccount();

    enum Type {

        DEBIT("DEBIT"),
        CREDIT("CREDIT");

        private final String type;

        private Type(String type) {
            this.type = type;
        }
        
        public String getType() {
            return this.type;
        }
    }

    enum Status {

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
