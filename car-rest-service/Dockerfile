FROM maven:3.8.6-eclipse-temurin-17-focal AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-focal
COPY --from=build /target/car-rest-service-0.0.1-SNAPSHOT.jar car-rest-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","car-rest-service.jar"]
