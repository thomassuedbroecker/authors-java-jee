package com.ibm.authors;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;

// Java logger
import java.util.logging.Logger;
// Java logger

// Tracing 
import org.eclipse.microprofile.opentracing.*;
// Tracing 


@Health
@ApplicationScoped
public class HealthEndpoint implements HealthCheck {
    Logger l = Logger.getLogger(HealthEndpoint.class.getName());
    
    @Traced
    @Override
    public HealthCheckResponse call() {
        l.info("... send HealthEndpoint information");
        System.out.println("... send HealthEndpoint information");
        return HealthCheckResponse.named("authors").withData("authors", "ok").up().build();
    }
}
