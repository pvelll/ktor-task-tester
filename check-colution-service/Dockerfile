FROM openjdk:19
LABEL authors="pavel"
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} check-colution-service-0.0.1.jar
ENTRYPOINT ["java","-jar","/check-colution-service-0.0.1.jar"]