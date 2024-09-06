FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR application
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN dos2unix mvnw
RUN ./mvnw package -DskipTests
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} orders-report.jar
RUN java -Djarmode=layertools -jar orders-report.jar extract

FROM eclipse-temurin:21-jre-alpine
LABEL authors="flaviohblima"
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]