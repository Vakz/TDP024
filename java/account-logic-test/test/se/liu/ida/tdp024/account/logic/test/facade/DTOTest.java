/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.test.facade;

import org.junit.Assert;
import org.junit.Test;
import se.liu.tdp024.account.logic.impl.dto.BankDTO;
import se.liu.tdp024.account.logic.impl.dto.PersonDTO;

/**
 *
 * @author frejo105
 */
public class DTOTest {
    @Test
    public void PersonDTOTest() {
        PersonDTO persondto = new PersonDTO();
        persondto.setName("person");
        Assert.assertEquals("person", persondto.getName());
        
        persondto.setKey("key");
        Assert.assertEquals("key", persondto.getKey());
    }
    
    @Test
    public void BankDTOTest() {
        BankDTO bankdto = new BankDTO();
        bankdto.setName("bank");
        Assert.assertEquals("bank", bankdto.getName());
        
        bankdto.setKey("key");
        Assert.assertEquals("key", bankdto.getKey());
    }
}
