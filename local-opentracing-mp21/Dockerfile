FROM maven:3.5-jdk-8 as BUILD
 
COPY src /usr/src/app/src
# Copy the pom file which does not contain the additional build information
COPY pom-docker.xml /usr/src/app
WORKDIR /usr/src/app
RUN mv pom-docker.xml pom.xml
WORKDIR /usr/src/app
RUN ls

# RUN mvn -version
RUN mvn -f /usr/src/app/pom.xml clean package

FROM open-liberty:microProfile2-java11


COPY liberty/server-opentracing-docker.xml /config/
WORKDIR /config/
RUN mv server-opentracing-docker.xml server.xml
WORKDIR /config/
RUN ls

# for opentracing
COPY liberty-opentracing-zipkintracer /opt/ol/wlp/usr/

COPY --from=BUILD /usr/src/app/target/authors-opentracing.war /config/apps/


EXPOSE 3000
EXPOSE 9411