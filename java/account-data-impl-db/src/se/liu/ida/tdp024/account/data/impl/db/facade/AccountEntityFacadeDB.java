package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

public class AccountEntityFacadeDB implements AccountEntityFacade {

    @Override
    public List<Account> find(String personKey) {
        EntityManager em = EMF.getEntityManager();
        Query query = em.createQuery("SELECT a FROM AccountDB a WHERE a.personKey = :keyParam");
        query.setParameter("keyParam", personKey);
        return query.getResultList();
    }

    @Override
    public Account create(Account.Type type, String personKey, String bankKey) throws IllegalArgumentException, Exception {
        EntityManager em = EMF.getEntityManager();
        
        EntityTransaction t = em.getTransaction();
        t.begin();
        
        Account account = new AccountDB();
        account.setType(type);
        try {
            account.setBankKey(bankKey);
            account.setPersonKey(personKey);
            em.persist(account);
            t.commit();
            return account;
        } catch (IllegalArgumentException e) {
            // Write to log
            throw e;
            
        } catch (Exception e) {
            // Write to log
            throw e;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
        
        
    }
    
}
