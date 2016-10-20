/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.tdp024.account.logic.impl.dto;

/**
 *
 * @author frejo105
 */
public class BankDTO {
    private String key;
    private String name;
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
