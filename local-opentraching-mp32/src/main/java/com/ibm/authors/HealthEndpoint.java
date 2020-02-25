package com.ibm.authors;

// Health
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Readiness
public class HealthEndpoint implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("authors-mp3-openshift").withData("authors-mp3-openshift", "ok").up().build();
    }
}
