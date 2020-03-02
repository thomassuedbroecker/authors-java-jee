package authortests;

import com.ibm.authors.Author;

// https://www.tomitribe.com/blog/overview-of-microprofile-rest-client/
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;


import java.io.UnsupportedEncodingException;
// Java
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLEncoder;

// JSON-B
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.*;

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
        String name = "Thomas";
        String requestURL = BASE_URL + "getauthor?name=" + name;

        try {
            String the_request = URLEncoder.encode(requestURL, "UTF-8").replace("+", "%20");
            URI baseURI = URI.create(BASE_URL);
            
            System.out.println("[TEST] -> Create RestClient");AuthorTestClient authorClient = RestClientBuilder.newBuilder().baseUri(baseURI).register(ExceptionMapperAuthors.class).build(AuthorTestClient.class);

            System.out.println("[TEST] -> Invoke GetAuthor");         
            
            // pure response result
            String result = authorClient.getAuthor(name);
            System.out.println("[TEST] Author string - result: " + result);

            // create json from result
            JsonbConfig config = new JsonbConfig().withAdapters(new AuthorJsonbAdapter());
            Jsonb jsonb = JsonbBuilder.create(config);
            Author author_json = jsonb.fromJson(result, Author.class);
            
            System.out.println("[TEST] author_json.class : " + author_json.toString());
            System.out.println("[TEST] author_json.name : " + author_json.getName());
          
            Assertions.assertEquals("Niklas Heidloff",
            author_json.getName(),"Not the expected value: Niklas Heidloff");
        
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException: "+ e.toString());
        } catch (Exception e) {
            System.out.println("[Exception] " + e.toString());
        }
    }
}


