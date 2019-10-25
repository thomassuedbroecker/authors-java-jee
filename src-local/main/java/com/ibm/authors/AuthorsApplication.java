package com.ibm.authors;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;
 
@ApplicationPath("v1")
public class AuthorsApplication extends Application {
    Logger l = Logger.getLogger(AuthorsApplication.class.getName());
    
    public AuthorsApplication(){
        System.out.println("... start AuthorsApplication");
	}
}