# run Maven build in Docker image
FROM maven:3-jdk-8-alpine as builder
WORKDIR /usr/src/app
COPY pom.xml pom.xml
COPY settings.xml settings.xml
RUN mvn -s settings.xml dependency:resolve-plugins dependency:resolve clean package -DskipTests --activate-profiles !default
COPY src/ src/
RUN mvn clean package -DskipTests

# create another image layer and run the app that was built
FROM openjdk:8-jdk-alpine as process-application

# Create app directory
WORKDIR /usr/src/app

COPY --from=builder /usr/src/app/target/camunda-poc-starter.jar ${WORKDIR}

#COPY target/camunda-poc-starter.jar ${WORKDIR}

ENTRYPOINT ["java","-Dspring.profiles.active=pwc","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/src/app/camunda-poc-starter.jar"]