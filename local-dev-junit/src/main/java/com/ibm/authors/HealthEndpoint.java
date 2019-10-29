package com.ibm.authors;

import javax.enterprise.context.ApplicationScoped;

// Health
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Health
@ApplicationScoped
public class HealthEndpoint implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        System.out.println("... HealthCheck");
        return HealthCheckResponse.named("authors").withData("authors", "ok").up().build();
    }
}
