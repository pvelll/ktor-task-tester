FROM openjdk:latest

COPY ./build/libs/com.sushkpavel.ktor-leetcode-0.0.1.jar /app/com.sushkpavel.ktor-leetcode-0.0.1.jar

WORKDIR /app

CMD ["java", "-jar", "com.sushkpavel.ktor-leetcode-0.0.1.jar"]