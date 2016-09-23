/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

/**
 *
 * @author frejo105
 */
public interface TransactionEntityFacade {
    
    public List<Transaction> transactions(int id);
    
    public boolean credit(int id, int amount);
    
    public boolean debit(int id, int amount);
    
}
