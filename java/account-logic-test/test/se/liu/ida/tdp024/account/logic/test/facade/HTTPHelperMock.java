/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.test.facade;

import se.liu.ida.tdp024.account.util.http.HTTPHelper;

/**
 *
 * @author frejo105
 */
public class HTTPHelperMock implements HTTPHelper {

    @Override
    public String get(String endpoint, String... parameters) {
        if (endpoint.endsWith("/find.name")) {
            if (parameters[1].equals("Emelie")) {
               return "{\"name\": \"Emelie\", \"key\": \"personkey\"}"; 
            } else if (parameters[1].equals("Swedbank")) {
                return "{\"name\": \"Swedbank\", \"key\": \"bankkey\"}"; 
            }
        }
        return "";
    }

    @Override
    public String postJSON(String endpoint, String[] queryParameters, String[] dataParameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
