package com.ibm.authors;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Test_GetAuthors {

    @Test
    public void testSuite() {
        this.testGetAuthors();
    }

    public void testGetAuthors() {
        static final String BASE_URL = "http://localhost/api/v1/";
        static final String AUTHOR_NAME="Niklas";
      
        AuthorsClient customRestClient = RestClientBuilder.newBuilder().baseUrl(apiUrl).register().build(AuthorsClient.class);
   }

    /**
     * <p>
     * Returns response information from the specified URL.
     * </p>
     * 
     * @param url
     *          - target URL.
     * @return Response object with the response from the specified URL.
     */
    private Response getResponse(String url) {
        return client.target(url).request().get();
    }

    /**
     * <p>
     * Asserts that the given URL has the correct response code of 200.
     * </p>
     * 
     * @param url
     *          - target URL.
     * @param response
     *          - response received from the target URL.
     */
    private void assertResponse(String url, Response response) {
        assertEquals("Incorrect response code from " + url, 200, response.getStatus());
    }

    /**
     * Asserts that the specified JVM system property is equivalent in both the
     * system and inventory services.
     * 
     * @param propertyName
     *          - name of the system property to check.
     * @param hostname
     *          - name of JVM's host.
     * @param expected
     *          - expected name.
     * @param actual
     *          - actual name.
     */
    private void assertProperty(String propertyName, String hostname,
            String expected, String actual) {
        assertEquals("JVM system property [" + propertyName + "] "
                + "in the system service does not match the one stored in "
                + "the inventory service for " + hostname, expected, actual);
    }

    /**
     * Makes a simple GET request to inventory/systems/localhost.
     */
    private void visitLocalhost() {
        Response response = this.getResponse(sysUrl + SYSTEM_PROPERTIES);
        this.assertResponse(sysUrl, response);
        response.close();

        Response targetResponse = client.target(invUrl + INVENTORY_SYSTEMS + "/localhost").request().get();
        targetResponse.close();
    }
}

