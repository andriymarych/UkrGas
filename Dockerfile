FROM arm64v8/eclipse-temurin:17-jre
WORKDIR /app
COPY . /app
ENTRYPOINT ["java","-jar","/app/target/gas-application-0.1.1.jar"]