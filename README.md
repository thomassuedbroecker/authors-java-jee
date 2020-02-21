**!!! UNDER CONSTRUCTION !!!**

# Authors Microservice in Java

Useful blog post: http://www.adam-bien.com/roller/abien/entry/using_microprofile_rest_client_for

Verify the running Authors service on the local machine.

* Get Author
```sh
$ curl -X GET "http://localhost:3000/api/v1/getauthor?name=Niklas%20Heidloff" -H "accept: application/json"
```

* Check health
```sh
curl http://localhost:3000/health
```

# Working with Visual Studio Code OpenLiberty and MicroProfile

In this gif you see how you can work in VS Code

![openliberty-microprofile-debug-in-vs-code](images/openliberty-microprofile-debug-in-vs-code.gif)

To do that what you see in the gif, you need to
- configure the Maven extension
- configure the [Debug extension for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-debug). Here you need the OpenLiberty debug port **7777**

```json
{
    "type": "java",
    "name": "Debug (Attach)",
    "request": "attach",
    "hostName": "localhost",
    "port": 7777
}
```

- Enable the [liberty-maven plugin](https://github.com/OpenLiberty/ci.maven) in the pom.xml and configure the server information, if needed.

```xml
	<build>
		....
        <!--Plugins -->
        <plugins>  
            <!-- Enable liberty-maven plugin -->
            <!-- tag::libertyMavenPlugin[] -->
            <plugin>
                <groupId>io.openliberty.tools</groupId>
                <artifactId>liberty-maven-plugin</artifactId>
				<!-- tag::libertyMavenConfiguration[] -->
				<configuration>
                	<serverName>authorsDevServer</serverName>
					<configFile>liberty/server.xml</configFile>
            	</configuration>
				<!-- end::libertyMavenConfiguration[] -->
            </plugin>
            <!-- end::libertyMavenPlugin[] -->
            <!-- Enable liberty-maven-plugin -->
        </plugins>
	</build>
```

- Add the [liberty dev extension](https://marketplace.visualstudio.com/items?itemName=Open-Liberty.liberty-dev-vscode-ext) 

_A VS Code extension for Liberty dev mode. The extension will detect your Liberty Maven project if it detects the **io.openliberty.tools:liberty-maven-plugin** .. in the pom.xml. Through the **Liberty Dev Dashboard explorer** on the side bar, you can start, stop, or interact with dev mode on all available Liberty dev projects in your workspace._

# Setup Visual Studio Code for Java Development and Debugging

VS Code extensions:

* [java extension pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)

    This extension also includes follwing extensions:

    * [vscode-java-debug](https://code.visualstudio.com/docs/java/java-debugging) and Configure attach to port '7777'

    * [Debugger for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-debug)

    * [maven for java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-maven)

# Visual Studio - OpenLiberty extention

* [liberty dev](https://marketplace.visualstudio.com/items?itemName=Open-Liberty.liberty-dev-vscode-ext)

# Visual Studio - code snippets

* [Use of user defined snippets](https://code.visualstudio.com/docs/editor/userdefinedsnippets) in local-dev-start->.vscode->GetAuthors.code-snippets

# Visual Studio - Remote Development

Here is a good blog post [Java development environments with containers](https://medium.com/@brunoborges/java-dev-environments-with-containers-66d6797b2753)

The configurtion I use in the `.devcontainer/devcontainer.json` file.

```json
{
	"name": "Java 11",
	"dockerFile": "Dockerfile",

	// Set *default* container specific settings.json values on container create.
	"settings": { 
		"terminal.integrated.shell.linux": "/bin/bash",
		"java.home": "/docker-java-home"
	},
	
	// Add the IDs of extensions you want installed when the container is created.
	"extensions": [
		"vscjava.vscode-java-pack"
	],

	// Use 'forwardPorts' to make a list of ports inside the container available locally.
	"forwardPorts": [8080,3000,7777],

	// Use 'postCreateCommand' to run commands after the container is created.
	"postCreateCommand": "java -version"

    ...
}
```



