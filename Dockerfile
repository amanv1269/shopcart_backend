#
# Build stage
#
FROM maven:4.0.0-jdk-21 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:21-jdk-slim
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
# ENV PORT=8080
EXPOSE 5453
ENTRYPOINT ["java","-jar","demo.jar"]
