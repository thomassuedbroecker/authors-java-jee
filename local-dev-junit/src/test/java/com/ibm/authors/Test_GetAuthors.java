package com.ibm.authors;

// http://www.adam-bien.com/roller/abien/entry/using_microprofile_rest_client_for
import com.ibm.authors.AuthorClient;

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

public class Test_GetAuthors {

    @Test
    public void testGetAuthor() throws MalformedURLException {
        String name = "Thomas";
        URI baseURI = URI.create("http://localhost/api/v1/getauthor?name="+name);

        AuthorClient client = RestClientBuilder.newBuilder().
                baseUri(baseURI).build(AuthorClient.class);
        System.out.println("GetAuthor");          
        Author result = client.getAuthor("thomas");
        assertNotNull(result);
    }
}


