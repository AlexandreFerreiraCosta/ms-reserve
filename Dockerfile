FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y

COPY . .

RUN apt-get install gradle
RUN apt-get update
RUN gradle clean build

EXPOSE 8080

COPY --from=build /libs/ms-reserve-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

