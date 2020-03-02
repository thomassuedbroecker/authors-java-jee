package com.ibm.authors;



// https://www.tomitribe.com/blog/overview-of-microprofile-rest-client/
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import com.ibm.authors.AuthorTestClient;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

@ApplicationScoped
public class Test_GetAuthors {
    @Inject
    @RestClient
    //private AuthorClient client;

    @Test
    public void testGetAuthor() throws MalformedURLException {
        String BASE_URL = "http://localhost:3000/api/v1/";
        String name = BASE_URL + "getauthor?name=Thomas";
        
        try {
            String the_name = URLEncoder.encode(name, "UTF-8").replace("+", "%20");
            URI baseURI = URI.create(BASE_URL);
            //URI baseURI = URI.create(BASE_URL + "getauthor?name=");
            // URL apiUrl = new URL(BASE_URL + "getauthor?name=" + the_name);
            System.out.println("TEST -> Create RestClient");
            //AuthorTestClient client = RestClientBuilder.newBuilder().baseUrl(apiUrl).register(ExceptionMapperAuthors.class).build(AuthorTestClient.class);
            AuthorTestClient client = RestClientBuilder.newBuilder().baseUri(baseURI).build(AuthorTestClient.class);
            System.out.println("TEST baseURI (Mapper disenabled) -> Invoke GetAuthor");         
            
            // AuthorTestClient client = RestClientBuilder.newBuilder().baseUri(baseURI).register(ExceptionMapperAuthors.class).build(AuthorTestClient.class);
            //System.out.println("TEST baseURI (Mapper enabled) -> Invoke GetAuthor");
            String result = client.getAuthor(the_name
            );

            System.out.println("Result: " + result);

            // System.out.println("Result: " + result.name);
            // System.out.println("Result: " + result.twitter);
            // System.out.println("Result: " + result.blog);

            Assertions.assertAll(result);
        
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException: "+ e.toString());
        } catch (Exception e) {
            System.out.println("Exception: "+ e.toString());
        }
    }
}


