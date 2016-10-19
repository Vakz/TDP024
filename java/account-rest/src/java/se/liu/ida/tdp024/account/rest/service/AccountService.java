package se.liu.ida.tdp024.account.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
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
    
    AccountLogger logger = new AccountLoggerImpl();
    AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB(logger), new HTTPHelperImpl(), logger);
    TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB(logger), logger);
    
    private void log(AccountLogger.AccountLoggerLevel level, String message) {
        logger.log(level, "AccountService", message);
    }
    
    @GET
    @Path("create")
    public Response create(@QueryParam("accounttype") String accounttype, @QueryParam("name") String name, @QueryParam("bank") String bank) {
        try {
            String res = accountLogicFacade.create(accounttype, bank, name);
            log(AccountLogger.AccountLoggerLevel.INFO, "SUCCESSFUL ACCOUNT CREATION: accounttype: " + accounttype + ", bank: " + bank + ", person: " + name);
            System.out.println(res);
            return Response.ok().entity(res).build();
        } catch (IllegalArgumentException e) {
            log(AccountLoggerLevel.ERROR, e.getMessage());
            return Response.status(Response.Status.OK).entity("FAILED").build();
        } catch (Exception e) {
            log(AccountLoggerLevel.EMERGENCY, "UNKNOWN CREATE ERROR: " + e.getMessage());
            return Response.serverError().build();
        }        
    }
    
    @GET
    @Path("credit")
    public Response credit(@QueryParam("id") int id, @QueryParam("amount") int amount) {
        try {
            String res = transactionLogicFacade.credit(id, amount);
            log(AccountLogger.AccountLoggerLevel.INFO, "SUCCESSFUL CREDIT: account: " + id + ", amount: " + amount);
            if (res.equals("FAILED")) throw new IllegalArgumentException("Invalid amount");
            return Response.ok(res).build();
        } catch (IllegalArgumentException e) {
            log(AccountLoggerLevel.ERROR, e.getMessage());
            return Response.status(Response.Status.OK).entity("FAILED").build();
        } catch (Exception e) {
            log(AccountLoggerLevel.EMERGENCY, "UNKNOWN CREDIT ERROR: " + e.getMessage());
            return Response.serverError().build();
        } 
    }
    
    @GET
    @Path("debit")
    public Response debit(@QueryParam("id") int id, @QueryParam("amount") int amount) {
        try {
            String res = transactionLogicFacade.debit(id, amount);
            log(AccountLogger.AccountLoggerLevel.INFO, "SUCCESSFUL DEBIT: account: " + id + ", amount: " + amount);
            //if (res.equals("FAILED")) throw new IllegalArgumentException("Invalid amount");
            return Response.ok(res).build();
        } catch (IllegalArgumentException e) {
            log(AccountLoggerLevel.ERROR, e.getMessage());
            return Response.status(Response.Status.OK).entity("FAILED").build();
        } catch (Exception e) {
            log(AccountLoggerLevel.EMERGENCY, "UNKNOWN DEBIT ERROR: " + e.getMessage());
            return Response.serverError().build();
        } 
    }
    
    @GET
    @Path("transactions")
    public Response transactions(@QueryParam("id") int id) {
        try {
            String res = transactionLogicFacade.transaction(id);
            return Response.ok(res).build();
        } catch (Exception e) {
            log(AccountLoggerLevel.EMERGENCY, "UNKNOWN TRANSACTION ERROR: " + e.getMessage());
            return Response.serverError().build();
        }
    }
    
    @GET
    @Path("find/name")
    public Response findAccounts(@QueryParam("name") String name) {
        try {
            String res = accountLogicFacade.find(name);
            return Response.ok(res).build();
        } catch (IllegalArgumentException e) {
            log(AccountLoggerLevel.ERROR, e.getMessage());
            return Response.status(Response.Status.OK).entity(e.getMessage()).build();
        } catch (Exception e) {
            log(AccountLoggerLevel.EMERGENCY, "UNKNOWN FIND ERROR: " + e.getMessage());
            return Response.serverError().build();
        }
    }
}
