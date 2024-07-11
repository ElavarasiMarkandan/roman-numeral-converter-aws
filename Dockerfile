FROM eclipse-temurin:17-jdk-alpine

LABEL maintainer="Elavarasi Markandan"
LABEL description="Spring boot Java app that exposes a REST API to convert a given integer to roman numeral"

EXPOSE 8080

RUN mkdir -p /app
WORKDIR /app

ARG JAR_FILE=target/*.jar

RUN wget -O apm-agent.jar https://repo1.maven.org/maven2/co/elastic/apm/elastic-apm-agent/1.50.0/elastic-apm-agent-1.50.0.jar

COPY ${JAR_FILE} /app/app.jar
COPY ../docker/app/startup.sh /app/startup.sh

RUN chmod +x /app/startup.sh

ENTRYPOINT [ "/bin/bash", "/app/startup.sh" ]