FROM openjdk:latest
ARG project_build_finalName=<Placeholder>
COPY "${project_build_finalName}.jar" ./app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]


#Execute it as this way on command line. passing the build argument.
#docker build --build-arg project_build_finalName="license-microservices-0.0.1-SNAPSHOT" -t lol .

## WHile building thorugh maven we can pass build args via configuration and buildArgs Tag.