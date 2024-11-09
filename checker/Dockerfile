FROM openjdk:19
LABEL authors="pavel"
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} check-solution-com.sushkpavel.service-0.0.1.jar
ENTRYPOINT ["java","-jar","/check-solution-com.sushkpavel.service-0.0.1.jar"]