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

    String transactions(int id);

    String credit(int id, int amount);

    String debit(int id, int amount);
}
