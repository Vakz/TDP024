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
        Account account = em.find(AccountDB.class, id);
        Query query = em.createQuery("SELECT t FROM TransactionDB t WHERE t.account = :account");
        query.setParameter("account", account);
        return query.getResultList();
    }

    @Override
    public Transaction.Status credit(int id, int amount) {
        EntityManager em = EMF.getEntityManager();
        
        Transaction.Status success = Transaction.Status.FAILED;
        Account account = em.find(AccountDB.class, id);
        try {
            if (amount <= 0) throw new IllegalArgumentException("Transaction amount must be positive");
            em.getTransaction().begin();
            account.setHoldings(account.getHoldings() + amount);
            em.merge(account);
            em.getTransaction().commit();
            success = Transaction.Status.OK;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                em.close();
            }
        }
        
        try {
            em.getTransaction().begin();
            Transaction transaction = new TransactionDB();
            transaction.setAmount(amount);
            transaction.setDate(new Date());
            transaction.setStatus(success);
            transaction.setType(Transaction.Type.CREDIT);
            transaction.setAccount(account);
            em.persist(transaction);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage()); 
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
        em.getTransaction().begin();
        Transaction.Status success = Transaction.Status.FAILED;
        Account account = em.find(AccountDB.class, id);
        try {
            if (amount <= 0) throw new IllegalArgumentException("Transaction amount must be positive");
            if (amount > account.getHoldings()) throw new IllegalArgumentException("Can't debit more than current account holdings");
            account.setHoldings(account.getHoldings() - amount);
            em.merge(account);
            success = Transaction.Status.OK;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        
        try {
            em.getTransaction().begin();
            Transaction transaction = new TransactionDB();
            transaction.setAmount(amount);
            transaction.setDate(new Date());
            transaction.setStatus(success);
            transaction.setType(Transaction.Type.DEBIT);
            transaction.setAccount(account);
            em.persist(transaction);
            em.getTransaction().commit();
            System.out.println("COMMITED");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
        return success;
    }
    
    
}
