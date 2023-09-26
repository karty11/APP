FROM openjdk:17

WORKDIR /app

COPY ./target/credentia-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 9090

CMD ["java", "-jar", "app.jar"]