FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
ADD target/backend-0.0.1-SNAPSHOT.jar /app/backend-0.0.1-SNAPSHOT.jar
ENV SPRING_PROFILES_ACTIVE=dev
EXPOSE 8080
ENTRYPOINT ["java","-jar","backend-0.0.1-SNAPSHOT.jar"]