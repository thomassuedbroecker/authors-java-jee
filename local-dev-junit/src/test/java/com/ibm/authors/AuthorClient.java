package com.ibm.authors;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/getauthor")
@RegisterRestClient
public interface AuthorClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Author getAuthor(@QueryParam("name") String name);
}