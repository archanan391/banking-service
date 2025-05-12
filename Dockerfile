FROM eclipse-temurin:21-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} banking-service-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/banking-service-1.0-SNAPSHOT.jar"]