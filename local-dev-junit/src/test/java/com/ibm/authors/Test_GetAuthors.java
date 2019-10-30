package com.ibm.authors;

// http://www.adam-bien.com/roller/abien/entry/using_microprofile_rest_client_for

import java.net.MalformedURLException;
import java.net.URI;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class TestClientGetAuthors {

    @Test
    public void init() throws MalformedURLException {
        URI baseURI = URI.create("http://localhost/api/v1/");
        AuthorClient client = RestClientBuilder.newBuilder().
                baseUri(baseURI).
                build(AuthorClient.class);                
        assertNotNull(client);
        String result = client.ping();
        assertNotNull(result);
    }
}


