**!!! UNDER CONSTRUCTION !!!**

# JUnit Testing on OpenLiberty development server, running a microservice based on MircoProfile 

Technical basics for how to develop a JUnit for our Authors microservice of the Cloud Native Starter example.

The Authors microservice has one RESTful api endpoint `getAuthor` with one parameter for the name of the Author. That endpoint returns Author data in a JSON format, based on given name in the parameter.

To invoke that endpoint we need a REST Client and we need to transform the JSON result in a Author data class.

Next we need to handle different values to for the parameter of the Author name, to run tests with a variations of names.

Then we need to verify the actual against the expected value and document the result.

**Need to know**

1. How to setup and run JUnit tests on the OpenLiberty development server?
2. How to convert JSON Data from a String in a Java Class with MicroProfile JSON-B?
3. How to create a REST Client with MicroProfile JAX-RS?
4. How to configure parameterized test in JUnit?
5. How to compare and report results in JUnit?

**Tools and frameworks**

* IDE: Visual Studio Code
* Server: Open Liberty
* Framework: MicroProfie
* Maven: Java project organization

---

# How to setup and run JUnit tests on the OpenLiberty development server?

To setup JUnit tests to run directly on the same OpenLiberty server as the microservice application you have to provide a `test` folder in the `src` folder. The image below shows the folders of that project.

![open-liberty-junit-01-folderstructure](images/open-liberty-junit-01-folderstructure.png)

In the `pom.xml` file you need to add the JUnit depencencies.
The `junit-jupiter-api` and the `junit-jupiter-engine`are the basics. With the `junit-jupiter-params` depencency we can define later a parameterized test. 

Here are the needed depencencies.

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
To run the development server with maven, we also add the serAlso the 

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

# How to convert JSON Data from a String in a Java Class with MicroProfile JSON-B?

When we get the result of the response of our endpoint `getAuthor` we got the data in a JSON format, but we what to handle the data of an Author in a Author class.

With Json-b we can define a JsonbAdapter class and overwritee the operations `adaptToJson` and `adaptFromJson`. 
In the operation`adaptFromJson` we define how to create a Author object a JSON object.

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