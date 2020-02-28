package com.ibm.authors;

// http://www.adam-bien.com/roller/abien/entry/using_microprofile_rest_client_for
import com.ibm.authors.AuthorTestClient;

// https://www.tomitribe.com/blog/overview-of-microprofile-rest-client/
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.io.UnsupportedEncodingException;
// Java
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

// Javax
import javax.json.Json;

// MicroProfile
import org.eclipse.microprofile.rest.client.RestClientBuilder;

// JUnit
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

@ApplicationScoped
public class Test_GetAuthors {
    @Inject
    @RestClient
    //private AuthorClient client;

    @Test
    public void testGetAuthor() throws MalformedURLException {
        String BASE_URL = "http://localhost:3000/api/v1/";
        String name = "Thomas";
        
        try {
            String the_name = URLEncoder.encode(name, "UTF-8").replace("+", "%20");
            // URI baseURI = URI.create("http://localhost:3000/api/v1/");
            URL apiUrl = new URL(BASE_URL + "getauthor?name=" + the_name);
            System.out.println("TEST -> Create RestClient");
            AuthorTestClient client = RestClientBuilder.newBuilder().baseUrl(apiUrl).build(AuthorTestClient.class);
            System.out.println("TEST -> Invoke GetAuthor");          
            
            Json result = client.getAuthor(name);

            System.out.println("Result: " +result.toString());

            // System.out.println("Result: " + result.name);
            // System.out.println("Result: " + result.twitter);
            // System.out.println("Result: " + result.blog);

            assertNotNull(result);
        
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException: "+ e.toString());
        }
    }
}


