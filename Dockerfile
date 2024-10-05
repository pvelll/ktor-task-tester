FROM openjdk:19
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} com.sushkpavel.ktor-leetcode-0.0.1.jar
ENTRYPOINT ["java","-jar","/com.sushkpavel.ktor-leetcode-0.0.1.jar"]