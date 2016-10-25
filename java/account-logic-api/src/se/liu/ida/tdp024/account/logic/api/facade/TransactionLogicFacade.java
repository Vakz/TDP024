/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

/**
 *
 * @author frejo105
 */
public interface TransactionLogicFacade {

    List<Transaction> transactions(int id);

    String credit(int id, int amount);

    String debit(int id, int amount);
}
