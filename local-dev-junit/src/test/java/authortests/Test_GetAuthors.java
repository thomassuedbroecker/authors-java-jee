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
// Verify Strings
import java.util.Objects;

// JSON-B
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.*;

// MicroProfile
import org.eclipse.microprofile.rest.client.RestClientBuilder;

// JUnit
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@ApplicationScoped
public class Test_GetAuthors {
    @Inject
    @RestClient
    @DisplayName("Test Authors.getAuthor()")
    @ParameterizedTest(name = "{index} => name=''{0},{1}''")
    @CsvSource({"Thomas,Thomas Suedbroecker",
                "Niklas,Niklas Heidloff",
                "Michael,Michael Heinrich"
              })  
    public void testGetAuthor(String 
    name, String expectedResult ) throws MalformedURLException {
        
        String BASE_URL = "http://localhost:3000/api/v1/";
        String requestURL = BASE_URL + "getauthor?name=" + name;

        try {
            String the_request = URLEncoder.encode(requestURL, "UTF-8").replace("+", "%20");
            URI baseURI = URI.create(BASE_URL);
            
            // Prepare test
            System.out.println("[TEST] -> Create RestClient");
            AuthorTestClient authorClient = RestClientBuilder.newBuilder().baseUri(baseURI).register(ExceptionMapperAuthors.class).build(AuthorTestClient.class);
     
            // Execute tests
            System.out.println("[TEST] -> Execute test");
            System.out.println("[TEST] -> Invoke GetAuthor: " + name); 
            
            // pure response result
            String response = authorClient.getAuthor(name);
            
            // use response to create json
            JsonbConfig config = new JsonbConfig().withAdapters(new AuthorJsonbAdapter());
            Jsonb jsonb = JsonbBuilder.create(config);
            Author author_json = jsonb.fromJson(response, Author.class);
            
            System.out.println("[TEST] Response - author_json.class : " + author_json.toString());
            System.out.println("[TEST] Response - author_json.name : " + author_json.getName());
            
            // verify response
            assertEquals(expectedResult, author_json.getName());

        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException: "+ e.toString());
        } catch (Exception e) {
            System.out.println("[Exception] " + e.toString());
        }
    }
}


