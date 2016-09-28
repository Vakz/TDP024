package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

public class AccountEntityFacadeDB implements AccountEntityFacade {

    @Override
    public List<Account> find(String name) {
        EntityManager em = EMF.getEntityManager();
        String personKey = ""; // Get personKey
        Query query = em.createQuery("SELECT a FROM AccountDB a WHERE a.personKey = :keyParam");
        query.setParameter("keyParam", personKey);
        return query.getResultList();
    }

    @Override
    public Account create(Account.Type type, String name, String bank) throws IllegalArgumentException, Exception {
        EntityManager em = EMF.getEntityManager();
        
        em.getTransaction().begin();
        
        Account account = new AccountDB();
        
        String personKey = ""; // Get person key
        String bankKey = ""; // Get bank key;
        account.setType(type);
        try {
            account.setBankKey(bankKey);
            account.setPersonKey(personKey);
            em.persist(account);
            em.getTransaction().commit();
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
        return account;
    }
    
}
