FROM maven:3-openjdk-17 as builder
WORKDIR /app
COPY ./pom.xml ./
COPY ./src ./src
RUN mvn clean install

FROM openjdk:17.0-jdk

WORKDIR /app
COPY --from=builder /app/target/app.jar ./app.jar
RUN groupadd appgroup
RUN useradd appuser
RUN usermod -a -G appgroup appuser
RUN chmod -R 774 /var/log/
USER appuser
EXPOSE 8080