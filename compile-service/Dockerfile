FROM openjdk:19

RUN apt-get update && apt-get install -y \
    g++ \
    gcc \
    make \
    python3 \
    python3-pip \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} compile-service-0.0.1.jar

ENTRYPOINT ["java", "-jar", "/app/compile-service-0.0.1.jar"]

