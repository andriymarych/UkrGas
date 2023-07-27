FROM arm64v8/eclipse-temurin:17-jdk
WORKDIR /app
COPY . /app

RUN ./mvnw -N wrapper:wrapper
RUN ./mvnw dependency:go-offline
RUN ./mvnw clean install -DskipTests

ENTRYPOINT ["java","-jar","/app/target/gas-application-0.1.1.jar"]