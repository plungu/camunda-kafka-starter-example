FROM openjdk:8-jdk-alpine

# Create app directory
WORKDIR /usr/src/app

#TODO: run maven build in docker image
#RUN mvn clean install -DskipTests

COPY target/camunda-allstate-sirius-demo-app.jar ${WORKDIR}

ENTRYPOINT ["java","-Dserver.port=8080","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/src/app/camunda-allstate-sirius-demo-app.jar"]
