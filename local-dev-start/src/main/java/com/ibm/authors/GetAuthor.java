package com.ibm.authors;

import javax.json.JsonObject;
import javax.json.Json;

// JAX-RS
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;

/* ************
@OpenAPIDefinition(info = @Info(title = "Authors Service", version = "1.0", description = "Authors Service APIs", contact = @Contact(url = "https://github.com/IBM/cloud-native-starter", name = "Niklas Heidloff"), license = @License(name = "License", url = "https://github.com/nheidloff/cloud-native-starter/blob/master/LICENSE")))
************ */
public class GetAuthor {


	/*
	@APIResponses(value = {
		@APIResponse(
	      responseCode = "404",
	      description = "Author Not Found"
	    ),
	    @APIResponse(
	      responseCode = "200",
	      description = "Author with requested name",
	      content = @Content(
	        mediaType = "application/json",
	        schema = @Schema(implementation = Author.class)
	      )
	    ),
	    @APIResponse(
	      responseCode = "500",
	      description = "Internal service error"  	      
	    )
	})
	@Operation(
		    summary = "Get specific author",
		    description = "Get specific author"
	)
	*/

	public Response getAuthor(
		    /*@Parameter(
            description = "The unique name of the author",
            required = true,
            example = "Niklas Heidloff",
			schema = @Schema(type = SchemaType.STRING))*/
			@QueryParam("name") String name) {
			
			Author author = new Author();
			author.name = "Niklas Heidloff";			
			author.twitter = "https://twitter.com/nheidloff";
			author.blog = "http://heidloff.net";

			System.out.println("... send getAuthor response");

			return Response.ok(this.createJson(author)).build();
	}
	
	private JsonObject createJson(Author author) {
		
		JsonObject output = Json.createObjectBuilder().add("name", author.name).add("twitter", author.twitter)
				.add("blog", author.blog).build();

		System.out.println("... create json object for Author");

		return output;
	}
}