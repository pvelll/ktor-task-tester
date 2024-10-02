FROM openjdk:latest

COPY ./build/libs/com.sushkpavel.ktor-leetcode-all.jar /app/com.sushkpavel.ktor-leetcode-all.jar

WORKDIR /app

CMD ["java", "-jar", "com.sushkpavel.ktor-leetcode-all.jar"]