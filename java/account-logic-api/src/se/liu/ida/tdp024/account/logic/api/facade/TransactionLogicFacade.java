/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.api.facade;

/**
 *
 * @author frejo105
 */
public interface TransactionLogicFacade {
    public String transaction(int id);
    
    public String credit(int id, int amount);
    
    public String debit(int id, int amount);
}
