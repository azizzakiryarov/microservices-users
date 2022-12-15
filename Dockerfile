#FROM registry.access.redhat.com/ubi8/openjdk-11:latest
#COPY target/user-service-0.0.1-SNAPSHOT.jar /tmp/build/inputs/user-service-0.0.1-SNAPSHOT.jar
#EXPOSE 8081
#WORKDIR /tmp/build/inputs
#ENTRYPOINT ["java","-jar","/user-service-0.0.1-SNAPSHOT.jar"]


# Build the application first using Maven
FROM maven:3.8-openjdk-11 as build
WORKDIR /app
COPY . .
RUN mvn install

# Inject the JAR file into a new container to keep the file small
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/user-service-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8081
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar app.jar"]