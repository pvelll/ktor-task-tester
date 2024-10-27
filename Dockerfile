FROM openjdk:19
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} com.sushkpavel.ktor-leetcode-0.0.1.jar
ENTRYPOINT ["java","--add-opens", "java.base/java.lang=ALL-UNNAMED","-jar","com.sushkpavel.ktor-leetcode-0.0.1.jar"]