package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLogger.AccountLoggerLevel;
import se.liu.tdp024.account.logic.impl.dto.BankDTO;
import se.liu.tdp024.account.logic.impl.dto.PersonDTO;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade;
    private HTTPHelper helper;
    private AccountLogger logger;
    
    private static final String PERSON_ENDPOINT = "http://localhost:8060";
    private static final String BANK_ENDPOINT = "http://localhost:8070";
    
    private void log(AccountLogger.AccountLoggerLevel level, String message) {
        logger.log(level, "AccountLogicFacade", message);
    }
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade, HTTPHelper helper, AccountLogger logger) {
        this.accountEntityFacade = accountEntityFacade;
        this.helper = helper;
        this.logger = logger;
    }

    @Override
    public String find(String name) throws IllegalArgumentException {
        PersonDTO person = getPerson(name);
        AccountJsonSerializer serializer = new AccountJsonSerializerImpl();
        List<Account> accounts = accountEntityFacade.find(person.getKey());
        return serializer.toJson(accounts);
    }

    @Override
    public String create(String type, String bank, String name) throws Exception{
        PersonDTO persondto = getPerson(name);
        BankDTO bankdto = getBank(bank);
        
        try {
           accountEntityFacade.create(Account.Type.fromString(type), persondto.getKey(), bankdto.getKey());
        } catch (Exception e) {
            log(AccountLoggerLevel.ERROR, "CREATE: bank: " + bank + ", person: " + name + ", error: " + e.getMessage());
            throw e;
        }
        
        return "OK";
    }
    
    private PersonDTO getPerson(String name) {
        if (name == null || name.trim().isEmpty()) {
            log(AccountLoggerLevel.WARNING, "Got empty person name as parameter");
            throw new IllegalArgumentException("Name cannot be empty");
        }
        final String url = PERSON_ENDPOINT + "/find.name";
        String json = helper.get(url, "name", name);
        AccountJsonSerializer serializer = new AccountJsonSerializerImpl();
        return serializer.fromJson(json, PersonDTO.class);
    }
    
    private BankDTO getBank(String name) {
        if (name == null || name.trim().isEmpty()) {
            log(AccountLoggerLevel.WARNING, "Got empty bank name as parameter");
            throw new IllegalArgumentException("Name cannot be empty");
        }
        final String url = BANK_ENDPOINT + "/find.name";
        String json = helper.get(url, "name", name);
        AccountJsonSerializer serializer = new AccountJsonSerializerImpl();
        return serializer.fromJson(json, BankDTO.class);
    }
    
}
