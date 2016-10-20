/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLogger.AccountLoggerLevel;

/**
 *
 * @author frejo105
 */
public class TransactionEntityFacadeDB implements TransactionEntityFacade {
    
    private final AccountLogger logger;
    
    private void log(AccountLogger.AccountLoggerLevel level, String message) {
        logger.log(level, "TransactionEntityFacade", message);
    }
    
    public TransactionEntityFacadeDB(AccountLogger logger) {
        this.logger = logger;
    }

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
        em.getTransaction().begin();
        Transaction.Status success = Transaction.Status.FAILED;
        Account account = em.find(AccountDB.class, id, LockModeType.PESSIMISTIC_WRITE);
        try {
            if (amount <= 0) {
                throw new IllegalArgumentException("Transaction amount must be positive");
            }
            account.setHoldings(account.getHoldings() + amount);
            em.merge(account);
            em.getTransaction().commit();
            success = Transaction.Status.OK;
        } catch (IllegalArgumentException e) {
            generateTransactionErrorLog(e.getMessage(), id, amount);
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } 
        
        try {
            Transaction transaction = new TransactionDB(id, Transaction.Type.CREDIT, amount, getDate(), success, account);
            em.getTransaction().begin();
            em.persist(transaction);
            em.getTransaction().commit();
            log(AccountLoggerLevel.INFO, "Inserted new credit transasction for account id " + id);
        } catch (RollbackException e) {
            generateTransactionErrorLog(e.getMessage(), id, amount);
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
        Account account = em.find(AccountDB.class, id, LockModeType.PESSIMISTIC_WRITE);
        try {
            if (amount <= 0) {
                throw new IllegalArgumentException("Transaction amount must be positive");
            }
            if (amount > account.getHoldings()) {
                throw new IllegalArgumentException("Can't debit more than current account holdings");
            }
            account.setHoldings(account.getHoldings() - amount);
            em.merge(account);
            em.getTransaction().commit();
            success = Transaction.Status.OK;
        } catch (IllegalArgumentException e) {
            generateTransactionErrorLog(e.getMessage(), id, amount); 
        } catch (RollbackException e) {
            generateTransactionErrorLog(e.getMessage(), id, amount);
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        
        try {
            Transaction transaction = new TransactionDB(id, Transaction.Type.DEBIT, amount, getDate(), success, account);
            em.getTransaction().begin();
            em.persist(transaction);
            em.getTransaction().commit();
            log(AccountLoggerLevel.INFO, "Inserted new debit transasction for account id " + id);
        } catch (RollbackException e) {
            generateTransactionErrorLog(e.getMessage(), id, amount);
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
        return success;
    }
    
    private void generateTransactionErrorLog(String error, int id, int amount) {
        log(AccountLoggerLevel.ERROR, error + ", id: " + id + ", amount: " + amount);
    }
    
    private String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime());
    }
}
