package com.ibm.authors;


// https://www.tomitribe.com/blog/overview-of-microprofile-rest-client/
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
// import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
// import com.ibm.authors.AuthorTestClient;

import java.io.UnsupportedEncodingException;
// Java
import java.net.MalformedURLException;
import java.net.URI;
// import java.net.URL;
import java.net.URLEncoder;


// JSON
// import javax.json.Json;

// JSON-B
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;


// JAX-RS
// import javax.ws.rs.*;

// MicroProfile
import org.eclipse.microprofile.rest.client.RestClientBuilder;

// JUnit
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

@ApplicationScoped
public class Test_GetAuthors {
    @Inject
    @RestClient

    @Test
    public void testGetAuthor() throws MalformedURLException {
        String BASE_URL = "http://localhost:3000/api/v1/";
        String requestURL = BASE_URL + "getauthor?name=Thomas";

        try {
            String the_request = URLEncoder.encode(requestURL, "UTF-8").replace("+", "%20");
            URI baseURI = URI.create(BASE_URL);
            
            System.out.println("TEST -> Create RestClient");AuthorTestClient authorClient = RestClientBuilder.newBuilder().baseUri(baseURI).register(ExceptionMapperAuthors.class).build(AuthorTestClient.class);

            System.out.println("TEST baseURI -> Invoke GetAuthor");         
            
            // pure response result
            String result = authorClient.getAuthor(the_request
            );
            System.out.println("Author string - result: " + result);

            // create json from result
            Jsonb jsonb = JsonbBuilder.create();
            Author author_json = jsonb.fromJson(result, Author.class);
            System.out.println("Result name: " + author_json.getName());
            
            Assertions.assertAll(author_json.getName());
        
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException: "+ e.toString());
        } catch (Exception e) {
            System.out.println("Exception: "+ e.toString());
        }
    }
}


