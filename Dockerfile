
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM tomcat:9.0-jdk17
# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file and verify it exists
COPY --from=build /app/target/Web-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Debug: List what's in webapps
RUN ls -la /usr/local/tomcat/webapps/

# Enable auto-deployment and make sure Tomcat starts properly
ENV CATALINA_OPTS="-Djava.awt.headless=true -server"

EXPOSE 8080

# Start Tomcat and show deployment status
CMD ["sh", "-c", "catalina.sh run & s