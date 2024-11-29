FROM openjdk:17
EXPOSE 8080
ADD target/obp-identity-service.jar obp-identity-service.jar
ENTRYPOINT ["java", "-jar", "/obp-identity-service.jar", "postgres_db:5432", "--spring.datasource.url=jdbc:postgresql://postgres_db:5432/online_banking_user_db"]