FROM openjdk:17-jdk-alpine

ARG MODULE_NAME
COPY ${MODULE_NAME}/build/libs/${MODULE_NAME}-0.0.1-SNAPSHOT.jar sport_companion.jar

ENTRYPOINT ["java", "-jar", "/sport_companion.jar"]