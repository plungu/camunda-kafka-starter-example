## Business Case: Renewals

Renewal use-case allows agents to manage renewal process through email. A company may manage hundreds of renewals which will come up for renewal at different times during the year. The renewal process will need to start well before the renewal has elapsed to make sure there is time to reassign the resource.

A deadline must be set to ensure the resource will be available as soon as possible if the current user does not want to renew the resource. The current user must be given multiple chances to renew up to the deadline.

It's necessary to track the communication sent to the user by the manager and system and responses from the user so it's easy and efficient for the manager to track the state of the renewal.

![Renewal Renewal BPMN](lease-renewal.png)

### Architecture

**_more to come ..._**

#### Renewals: ReactJS UI Integration

The Maven frontend-maven-plugin configured in pom.xml is used to build the ReactJS app. The plugin creates a bundle.js file which ends up in `src/main/resources/static/built/bundle.js`. The static directory makes static resources such as JS and HTML available to the java app.

The Java application boot-straps the ReactJS App through Thymeleaf a java/spring frontend framework. The templates directory `src/main/resources/templates/app.html` has a HTML file app.html which calls the React app through a `<script />` tag loading the HTML into the react div `<div id="react"></div>`

Thymeleaf ties the Java frontend together using a Spring controller. `src/main/java/com/camunda/react/starter/controller/HomeController.java`. Mapping the app context to /home and calling the app.html.

**Visit `http://<server>:<port>/home` to access the React app.**
