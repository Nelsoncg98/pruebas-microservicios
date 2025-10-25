# ----- Build stage -----
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

# ----- Runtime stage -----
FROM eclipse-temurin:21-jre
WORKDIR /app
ENV JAVA_OPTS=""
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8087
CMD ["sh","-c","java $JAVA_OPTS -Dserver.port=${PORT:-8087} -jar /app/app.jar"]
