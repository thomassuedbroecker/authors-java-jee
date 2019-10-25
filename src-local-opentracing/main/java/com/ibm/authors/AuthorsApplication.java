package com.ibm.authors;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;
// Java logger
import java.util.logging.Logger;
// Java logger
 
@ApplicationPath("v1")
public class AuthorsApplication extends Application {
    Logger l = Logger.getLogger(AuthorsApplication.class.getName());
    
    public AuthorsApplication(){
        l.info("... start AuthorsApplication");
        System.out.println("... start AuthorsApplication");
	}
}