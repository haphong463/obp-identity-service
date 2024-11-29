FROM openjdk:17
EXPOSE 8080
ADD target/obp-identity-service.jar obp-identity-service.jar
ENTRYPOINT ["java", "-jar", "/obp-identity-service.jar"]