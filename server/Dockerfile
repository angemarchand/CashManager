# Étape 1 : Construire le projet avec Maven
FROM --platform=linux/amd64 maven:3.9.8-amazoncorretto-21-debian-bookworm AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Utiliser l'image openjdk pour exécuter l'application
FROM openjdk:21
COPY --from=builder /app/target/ /app/
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/server-0.0.1-SNAPSHOT.jar"]