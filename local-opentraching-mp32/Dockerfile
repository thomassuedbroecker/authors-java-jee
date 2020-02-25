FROM maven:3.6.3-jdk-8-openj9
 
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package


#FROM open-liberty:19.0.0.9-kernel-java11
FROM open-liberty:full

# for opentracing
COPY liberty-opentracing-zipkintracer /opt/ol/wlp/usr/

COPY liberty/server.xml /config/

COPY --from=0 /usr/src/app/target/authors-mp3-openshift.war /config/apps/

# This script will add the requested XML snippets, grow image to be fit-for-purpose and apply interim fixes
# https://github.com/WASdev/ci.docker
RUN configure.sh