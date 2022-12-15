FROM registry.access.redhat.com/ubi8/openjdk-11:latest
COPY target/user-service-0.0.1-SNAPSHOT.jar /opt/user-service-0.0.1-SNAPSHOT.jar
EXPOSE 8081
WORKDIR /opt
ENTRYPOINT ["java","-jar","/user-service-0.0.1-SNAPSHOT.jar"]