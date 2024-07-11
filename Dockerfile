FROM eclipse-temurin:17-jdk-alpine
RUN apk add curl
VOLUME /tmp
EXPOSE 8080
ADD target/roman-numeral-converter-aws-0.0.1-SNAPSHOT.jar roman-numeral-converter-aws-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/roman-numeral-converter-aws-0.0.1-SNAPSHOT.jar"]