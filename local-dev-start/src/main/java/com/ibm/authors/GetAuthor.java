package com.ibm.authors;

// JSON-B
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

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
			
			Author author = new Author("Niklas Heidloff", 
									   "https://twitter.com/nheidloff", 
									   "http://heidloff.net");
			
			Jsonb jsonb = JsonbBuilder.create();
			String author_json = jsonb.toJson(author); 
			author_json = jsonb.toJson(author); 

			System.out.println("... send getAuthor response");

			return Response.ok(author_json).build();
	}
}