package com.ibm.authors;

// http://www.adam-bien.com/roller/abien/entry/using_microprofile_rest_client_for
import com.ibm.authors.AuthorClient;

// https://www.tomitribe.com/blog/overview-of-microprofile-rest-client/
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


// Java
import java.net.MalformedURLException;
import java.net.URI;

// Javax
import javax.json.Json;

// MicroProfile
import org.eclipse.microprofile.rest.client.RestClientBuilder;

// JUnit
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

@ApplicationScoped
public class Test_GetAuthors {
    //@Inject
    //@RestClient
    //private AuthorClient client;

    @Test
    public void testGetAuthor() throws MalformedURLException {
        String name = "Thomas";
        URI baseURI = URI.create("http://localhost/api/v1/");
        // getauthor?name="+name
        System.out.println("Create RestClient");
        AuthorClient client = RestClientBuilder.newBuilder().baseUri(baseURI).build(AuthorClient.class);
        System.out.println("GetAuthor");          
        Author result = client.getAuthor("thomas");
        assertNotNull(result);
    }
}


