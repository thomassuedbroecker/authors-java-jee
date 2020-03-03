package authortests;

import com.ibm.authors.Author;

// https://www.tomitribe.com/blog/overview-of-microprofile-rest-client/
import org.eclipse.microprofile.rest.client.inject.RestClient;

// Java
import java.net.URI;

// JSON-B
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

// MicroProfile
import org.eclipse.microprofile.rest.client.RestClientBuilder;

// JUnit
import static org.junit.jupiter.api.Assertions.assertEquals;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

//@ApplicationScoped
public class Test_GetAuthors {
    @RestClient
    @DisplayName("Test Authors.getAuthor()")
    @ParameterizedTest(name = "{index} => name=''{0},{1}''")
    @CsvSource({"Thomas,Thomas Suedbroecker",
                "Niklas,Niklas Heidloff",
                "Michael,Michael Heinrich"
              })  
    public void testGetAuthor(
        String name, 
        String expectedResult ) {
        
        String BASE_URL = "http://localhost:3000/api/v1/";
        URI baseURI = URI.create(BASE_URL);
            
        // Prepare test
        System.out.println("[TEST] -> Create RestClient");
        AuthorTestClient authorClient = RestClientBuilder.newBuilder().baseUri(baseURI).build(AuthorTestClient.class);
     
        // Execute tests
        System.out.println("[TEST] -> Execute test");
        System.out.println("[TEST] -> Invoke GetAuthor: " + name); 
            
        // pure response result
        String response = authorClient.getAuthor(name);
        System.out.println("[TEST] Response : " + response);   
            
        // use response to create json
        System.out.println("[TEST] Create Jsonb config based on Adapter");
        JsonbConfig config = new JsonbConfig().withAdapters(new AuthorJsonbAdapter());
        System.out.println("[TEST] Create Jsonb Object");
        Jsonb jsonb = JsonbBuilder.create(config);
        System.out.println("[TEST] Create Author class instance using Jsonb Object from the response");
        Author author_json = jsonb.fromJson(response, Author.class);
        System.out.println("[TEST] Created author_json.class : " + author_json.toString());
        System.out.println("[TEST] Access the Auther class instance - author_json.name : " + author_json.getName());
            
        // verify response
        System.out.println("[TEST] Verify");
        assertEquals(expectedResult, author_json.getName());
    }
}