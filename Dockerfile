FROM maven:3.6.3-openjdk-8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package

FROM tomcat:9.0-jdk8
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/Web.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]