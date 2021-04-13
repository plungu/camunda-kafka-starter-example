# run Maven build in Docker image
FROM maven:3-jdk-8 as builder
WORKDIR /usr/src/app
COPY pom.xml pom.xml
COPY settings.xml settings.xml
COPY package.json package.json
COPY webpack.config.js webpack.config.js
COPY .env .env
COPY src/ src/

RUN mvn -s settings.xml dependency:resolve-plugins dependency:resolve clean package -DskipTests --activate-profiles !default
RUN mvn clean package -DskipTests

# create another image layer and run the app that was built
FROM openjdk:8-jdk as process-application

# Create app directory
WORKDIR /usr/src/app

# copy the built jar to the new image
COPY --from=builder /usr/src/app/target/camunda-poc-starter.jar ${WORKDIR}

ENTRYPOINT ["java","-Dspring.profiles.active=servicerequest,cors,integration,prod","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/src/app/camunda-poc-starter.jar"]
