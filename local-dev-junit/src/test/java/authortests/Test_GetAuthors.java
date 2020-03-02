package authortests;

import com.ibm.authors.Author;

// https://www.tomitribe.com/blog/overview-of-microprofile-rest-client/
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

// Execption
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
        String [] names = {"Niklas","Thomas"}; // Test data
        String requestURL = BASE_URL + "getauthor?name=" + names[0];

        try {
            String the_request = URLEncoder.encode(requestURL, "UTF-8").replace("+", "%20");
            URI baseURI = URI.create(BASE_URL);
            
            // Prepare test
            System.out.println("[TEST] -> Create RestClient");AuthorTestClient authorClient = RestClientBuilder.newBuilder().baseUri(baseURI).register(ExceptionMapperAuthors.class).build(AuthorTestClient.class);
     
            // Execute tests
            System.out.println("[TEST] -> Execute tests");
            for(int i=0; i<2; i++) {
              System.out.println("[TEST] -> Invoke GetAuthor: " + names[i]); 
              // pure response result
              String response = authorClient.getAuthor(names[i]);
              System.out.println("[TEST] Author string - result("+i+ ") : " + response);

              // use response to create json
              JsonbConfig config = new JsonbConfig().withAdapters(new AuthorJsonbAdapter());
              Jsonb jsonb = JsonbBuilder.create(config);
              Author author_json = jsonb.fromJson(response, Author.class);
            
              System.out.println("[TEST] Response - author_json.class : " + author_json.toString());
              System.out.println("[TEST] Response - author_json.name : " + author_json.getName());
             
              // verify response
              Assertions.assertEquals("Niklas Heidloff",
              author_json.getName(),"Not the expected value: Niklas Heidloff");
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException: "+ e.toString());
        } catch (Exception e) {
            System.out.println("[Exception] " + e.toString());
        }
    }
}


