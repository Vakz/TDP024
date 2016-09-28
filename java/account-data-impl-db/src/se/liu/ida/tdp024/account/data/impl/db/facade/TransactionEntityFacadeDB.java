/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

/**
 *
 * @author frejo105
 */
public class TransactionEntityFacadeDB implements TransactionEntityFacade {

    @Override
    public List<Transaction> transactions(int id) {
        EntityManager em = EMF.getEntityManager();
        
        Query query = em.createQuery("SELECT t FROM TransactionDB t, AccountDB a WHERE t.account = :id AND a.id = :id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public Transaction.Status credit(int id, int amount) {
        EntityManager em = EMF.getEntityManager();
        Transaction.Status success = Transaction.Status.FAILED;
        Account account = em.find(AccountDB.class, id);
        try {
            account.setHoldings(account.getHoldings() + amount);
            em.persist(account);
            em.getTransaction().commit();
            success = Transaction.Status.OK;
        } catch (IllegalArgumentException e) {
            // Write to log
        } catch (Exception e) {
            System.out.println("Oh no");
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                em.close();
            }
        }
        
        try {
            Transaction transaction = new TransactionDB();
            transaction.setAmount(amount);
            transaction.setDate(new Date());
            transaction.setStatus(success);
            transaction.setType(Transaction.Type.CREDIT);
            em.persist(transaction);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            
        } catch (Exception e) {
            
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
        return success;
    }

    @Override
    public Transaction.Status debit(int id, int amount) {
        EntityManager em = EMF.getEntityManager();
        Transaction.Status success = Transaction.Status.FAILED;
        Account account = em.find(AccountDB.class, id);
        try {
            account.setHoldings(account.getHoldings() - amount);
            em.persist(account);
            success = Transaction.Status.OK;
        } catch (IllegalArgumentException e) {
            // Write to log
        } catch (Exception e) {
            // Write to log
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        
        try {
            Transaction transaction = new TransactionDB();
            transaction.setAmount(amount);
            transaction.setDate(new Date());
            transaction.setStatus(success);
            transaction.setType(Transaction.Type.DEBIT);
            em.persist(transaction);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            
        } catch (Exception e) {
            
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
        return success;
    }
    
    
}
