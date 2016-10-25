package se.liu.ida.tdp024.account.rest.service;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLogger.AccountLoggerLevel;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerImpl;

@Path("/account")
public class AccountService {
    
    private final AccountLogger logger = new AccountLoggerImpl();
    private final AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB(logger), new HTTPHelperImpl(), logger);
    private final TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB(logger), logger);
    private static final String FAILURE_RESPONSE = "FAILED";
   
    private void log(AccountLogger.AccountLoggerLevel level, String message) {
        logger.log(level, "AccountService", message);
    }
    
    @GET
    @Path("create")
    public Response create(@QueryParam("accounttype") String accounttype, @QueryParam("name") String name, @QueryParam("bank") String bank) {
        try {
            String res = accountLogicFacade.create(accounttype, bank, name);
            log(AccountLogger.AccountLoggerLevel.INFO, "SUCCESSFUL ACCOUNT CREATION: accounttype: " + accounttype + ", bank: " + bank + ", person: " + name);
            return Response.ok().entity(res).build();
        } catch (IllegalArgumentException e) {
            log(AccountLoggerLevel.ERROR, e.getMessage());
            return Response.status(Response.Status.OK).entity(FAILURE_RESPONSE).build();
        }       
    }
    
    
    @GET
    @Path("credit")
    public Response credit(@QueryParam("id") int id, @QueryParam("amount") int amount) {
        try {
            String res = transactionLogicFacade.credit(id, amount);
            log(AccountLogger.AccountLoggerLevel.INFO, "SUCCESSFUL CREDIT: account: " + id + ", amount: " + amount);
            if (res.equals(FAILURE_RESPONSE)) {
                throw new IllegalArgumentException("Invalid amount");
            }
            return Response.ok(res).build();
        } catch (IllegalArgumentException e) {
            log(AccountLoggerLevel.ERROR, e.getMessage());
            return Response.status(Response.Status.OK).entity(FAILURE_RESPONSE).build();
        }
    }
    
    @GET
    @Path("debit")
    public Response debit(@QueryParam("id") int id, @QueryParam("amount") int amount) {
        String res = transactionLogicFacade.debit(id, amount);
        log(AccountLogger.AccountLoggerLevel.INFO, "SUCCESSFUL DEBIT: account: " + id + ", amount: " + amount);
        return Response.ok(res).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("transactions")
    public Response transactions(@QueryParam("id") int id) {
        List<Transaction> transactions = transactionLogicFacade.transactions(id);
        log(AccountLogger.AccountLoggerLevel.INFO, "SUCCESSFUL FIND TRANSACTIONS. ID: " + id + ", results: " + transactions.size());
        return Response.ok().entity(transactions).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("find/name")
    public Response findAccounts(@QueryParam("name") String name) {
        List<Account> accounts = accountLogicFacade.find(name);
        log(AccountLogger.AccountLoggerLevel.INFO, "SUCCESSFUL FIND ACCOUNTS. Name: " + name + ", results: " + accounts.size());
        return Response.ok().entity(accounts).build();
    }
}
