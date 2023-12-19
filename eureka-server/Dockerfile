FROM openjdk:17

WORKDIR /app

COPY build/eureka-server-0.0.1-SNAPSHOT.jar eureka-server-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "eureka-server-0.0.1-SNAPSHOT.jar"]