FROM maven:3.8.6 AS build
WORKDIR /app

COPY pom.xml .

COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/src/main/resources /app/resources

EXPOSE 8080

ENV SPRING_THYMELEAF_PREFIX=file:/app/resources/templates/
ENV SPRING_RESOURCES_STATIC_LOCATIONS=file:/app/resources/static/

ENTRYPOINT ["java", "-jar", "app.jar"]
