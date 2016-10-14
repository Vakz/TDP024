package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;
import se.liu.tdp024.account.logic.impl.dto.BankDTO;
import se.liu.tdp024.account.logic.impl.dto.PersonDTO;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade;
    private HTTPHelper helper;
    
    private static final String PERSON_ENDPOINT = "http://localhost:8060";
    private static final String BANK_ENDPOINT = "http://localhost:8070";
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade, HTTPHelper helper) {
        this.accountEntityFacade = accountEntityFacade;
        this.helper = helper;
    }

    @Override
    public String find(String name) throws IllegalArgumentException {
        PersonDTO person = getPerson(name);
        AccountJsonSerializer serializer = new AccountJsonSerializerImpl();
        List<Account> accounts = accountEntityFacade.find(person.getKey());
        return serializer.toJson(accounts);
    }

    @Override
    public String create(String type, String bank, String name) throws IllegalArgumentException {
        PersonDTO persondto = getPerson(name);
        BankDTO bankdto = getBank(bank);
        
        try {
           accountEntityFacade.create(Account.Type.fromString(type), persondto.getKey(), bankdto.getKey());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "FAILED";
        }
        
        return "OK";
    }
    
    private PersonDTO getPerson(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        final String url = PERSON_ENDPOINT + "/find.name";
        String json = helper.get(url, "name", name);
        AccountJsonSerializer serializer = new AccountJsonSerializerImpl();
        return serializer.fromJson(json, PersonDTO.class);
    }
    
    private BankDTO getBank(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        final String url = BANK_ENDPOINT + "/find.name";
        String json = helper.get(url, "name", name);
        AccountJsonSerializer serializer = new AccountJsonSerializerImpl();
        return serializer.fromJson(json, BankDTO.class);
    }
    
}
