**!!! UNDER CONSTRUCTION !!!**

# JUnit Testing on OpenLiberty development server, running a microservice based on MircoProfile 

The project does only contain technical basics: how to develop a JUnit for the Authors microservice of the Cloud Native Starter example.

The Authors microservice has one RESTful api endpoint called `getAuthor`. The endpoint provides one parameter for the Author name. The endpoint returns Author data in a JSON format.

These are the steps we need to understand and to realize:

1. To invoke that REST endpoint of the Authors microservice we need a REST Client.

2. Then we need to transform the JSON response to a Author data class. 

3. Next we need to handle different values to invoke different values for the parameter of the Author name, to run tests with a variations of names.

4. Then we need to verify the actual value against the expected value and document the result.

**To realize what we want to do, we need to know**

1. How to setup and run JUnit tests on the OpenLiberty development server?
2. How to convert JSON Data from a String in a Author Java instance with JSON-B (MicroProfile)?
3. How to create a REST Client with JAX-RS (MicroProfile)?
4. How to configure parameterized a test in JUnit?
5. How to compare and report results in JUnit?

**Tools and frameworks**

* IDE: Visual Studio Code
* Server: Open Liberty
* Framework: MicroProfie
* Java project organization: Maven

---

# How to setup and run JUnit tests on the OpenLiberty development server?

To setup JUnit tests and run them directly on the same OpenLiberty server as the microservice application, we have to provide a `test` folder in the `src` folder of our Java project. The image below shows the folders of my example project.

![open-liberty-junit-01-folderstructure](images/open-liberty-junit-01-folderstructure.png)

These are the classes you see in the image above:

**`com.ibm.authors` Package for Authors microservice**

* `AuthorsApplication` class repesents the JAX-RS RESTful web application.
* `Author` class repesents the data structure we use for the Author.
* `GetAuthor` class repesents the REST API Endpoint.
* `HealthEndpoint` class repesents the support readiness probes for Kubernetes.

**`authortests` Package for the JUnit test of the Authors microservice**

* `AuthorJsonbAdapter` class repesents JSON-B adapter for a JSON-B mapping configuration.
* `AuthorTestClient` class repesents the REST Client of the Authors microservice.
* `Test_GetAuthors` class repesents the JUnit test which will be executed for a test run.



In the `pom.xml` file you need to add the JUnit depencencies.
The `junit-jupiter-api` and the `junit-jupiter-engine` are the basics for the Unit tests. With the `junit-jupiter-params` depencency we can define later a parameterized test. 

Here are the needed depencencies for JUnit in the `pom.xml`.

```xml
	<!-- JUnit Test --> 
	<dependency>
		<groupId>org.junit.jupiter</groupId>
		<artifactId>junit-jupiter-api</artifactId>
		<version>5.6.0</version>
		<scope>test</scope>
	</dependency>
	<dependency>
		<groupId>org.junit.jupiter</groupId>
		<artifactId>junit-jupiter-engine</artifactId>
		<version>5.6.0</version>
		<scope>test</scope>
	</dependency>
	<dependency>
		<groupId>org.junit.jupiter</groupId>
		<artifactId>junit-jupiter-params</artifactId>
		<version>5.6.0</version>
		<scope>test</scope>
	</dependency>
	<!-- JUnit Test -->
```

Here are the needed plugins for the build `maven-surefire-plugin` and `maven-failsafe-plugin`. 

```xml
    <!-- JUNIT  -->
	<plugin>
		<artifactId>maven-surefire-plugin</artifactId>
		<version>2.22.2</version>
	</plugin>
	<plugin>
		<artifactId>maven-failsafe-plugin</artifactId>
		<version>2.22.2</version>
	</plugin>
	<!-- JUNIT -->
```

To start the OpenLiberty server later with maven we add the `liberty-maven-plugin` to the `pom.xml` file.

```xml
    <!-- Enable liberty-maven plugin -->
    <plugin>
        <groupId>io.openliberty.tools</groupId>
        <artifactId>liberty-maven-plugin</artifactId>
		<version>3.1</version>
		<!-- libertyMavenConfiguration -->
		<configuration>
            <serverName>authorsDevJUnitServer</serverName>
			<configFile>liberty/server.xml</configFile>
        </configuration>
	    <!-- libertyMavenConfiguration -->
    </plugin>
    <!-- Enable liberty-maven-plugin -->
```

---

# How to convert JSON Data from a String in a Java Class with JSON-B?

When we get the result of the response of our endpoint `getAuthor` we got the data in a JSON format, but we want use data in an instance of a Author class.

With Json-b we can define a [JsonbAdapter](https://www.eclipsecon.org/na2016/sites/default/files/slides/JSONB%20-%20EclipseCon%202016.pdf) class and override the operations `adaptToJson` and `adaptFromJson`. 
In the operation`adaptFromJson` we define how to create a Author object from a JSON Object.

```java
import com.ibm.authors.Author;

// JSON-B
import javax.json.bind.adapter.JsonbAdapter;
// JSON
import javax.json.JsonObject;
import javax.json.Json;

public class AuthorJsonbAdapter implements JsonbAdapter<Author, JsonObject> {
 
    @Override
    public JsonObject adaptToJson(final Author author) throws Exception {
        return Json.createObjectBuilder()
        .add("blog", author.getBlog())
        .add("name", author.getName())
        .add("pages", author.getTwitter()).build();
    }

    @Override
    public Author adaptFromJson(final JsonObject jsonObject) throws Exception {
        final Author author = new Author();
        author.setBlog(jsonObject.getString("blog"));
        author.setName(jsonObject.getString("name"));
        author.setTwitter(jsonObject.getString("twitter"));
        return author;
    }
}
```

---

# How to create a REST Client with JAX-RS (MicroProfile)?

Here you see the REST Client interface definition of the REST Endpoint of our Authors microservice.

I want to highlight that, here we define our expected return value for of the Authors microservice response with a `String

```java
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/getauthor")
@RegisterRestClient
public interface AuthorTestClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAuthor(@QueryParam("name") String name);
}
```

---

# Additional resources

## MicroProfile RestClient

* [Tomitribe (Blog post)](https://www.tomitribe.com/blog/overview-of-microprofile-rest-client/)

## JUnit

Configure OpenLiberty:

* [Setup Unit Tests in OpenLiberty](https://github.com/OpenLiberty/open-liberty/wiki/Unit-Tests)

Useful blog posts:

* [Adam-Bien (Blog post)](http://www.adam-bien.com/roller/abien/entry/using_microprofile_rest_client_for)

* [Petri Kainulainen (Blog post)](https://www.petrikainulainen.net/programming/testing/junit-5-tutorial-writing-parameterized-tests/)

* [Sebastian Daschner (YouTube)](https://www.youtube.com/watch?v=JPctzdfxeXo)

* [JUnit user-guide running tests is vscode](https://junit.org/junit5/docs/current/user-guide/#running-tests-ide-vscode)

## Jsonb

* [RIECKPIL](https://rieckpil.de/whatis-json-binding-json-b/)

* [3 ways to convert String to JSON object in Java?](https://www.java67.com/2016/10/3-ways-to-convert-string-to-json-object-in-java.html)