

[![GitHub tag (latest SemVer pre-release)](https://img.shields.io/github/v/tag/andriymarych/gas-app)](https://github.com/andriymarych/gas-app/releases)
[![GitHub last commit (by committer)](https://img.shields.io/github/last-commit/andriymarych/gas-app)](https://github.com/andriymarych/gas-app/commits/)
[![GitHub commit activity (branch)](https://img.shields.io/github/commit-activity/m/andriymarych/gas-app)](https://github.com/andriymarych/gas-app/commits/)
[![GitHub repo size](https://img.shields.io/github/repo-size/andriymarych/gas-app)](https://github.com/andriymarych/gas-app/)

<div align="center">
  <a href="https://github.com/andriymarych/gas-app">
 <img width="1144" alt="UkrGasLogo" src="https://github.com/andriymarych/gas-app/assets/87490528/9ff4d682-6983-4f3f-bf9f-04f5cb5ee958">
  </a>
</div>

<h1 align="center">
  Ukr Gas
</h1>

<br>
<div align="center">
<i>Ukr Gas is a  web application that provides convenient and prompt service for gas consumers</i>  :fuelpump:
</div>
<br>


## Table of Contents 📋

- [About](#about-)
- [Technologies](#technologies-)
- [Features](#features-)
- [Getting Started](#getting-started-)
    - [Installation](#installation-)
    - [Usage](#usage-)
- [Documentation](#documentation-)
- [Screenshots](#screenshots-)
- [Contacts](#contacts-)



## About 🔎

<br>
This repository is home to the Ukr Gas web development project,
which aims to provide gas consumers with all the popular tools for managing their personal accounts<br>
Under the hood, this program implements the following business logic:
<br>
<br>

* Every first day of the month, the notification service sends a template reminder to users to submit meter readings on time
* Users can submit indicators only until the 15th of the month
* After entering the correct meter reading, access to re-transmission of the meter reading is prohibited
* All values entered by the user are checked for correctness in several stages
* If the user does not submit the meter readings on time, the meter value is automatically determined based on the penalty
* The automatic calculation of users' settlement payments is carried out at 4 a.m. on the 15th of each month.
  This process calculates the volume of gas consumed for the month, the amount of payments made, and the final amount accrued for payment
* Every day at 00:00, the fuel prices are updated from the third-party API service https://collectapi.com/api/gasPrice/gas-prices-api<br>
  Based on the data obtained, fuel prices are compared with the prices of the previous day
* In order to correctly display fuel prices on the main page in accordance with the user's localization, the exchange rate is updated daily at 00:00 from a third-party API service https://currencyapi.com/

## Technologies 🧰
* <b>Spring</b> (Core, Data JPA, Boot, MVC)
* <b>Hibernate</b>
* <b>PostgreSQL</b>
* <b>Figma</b>
* <b>Thymeleaf</b>
* <b>HTML/CSS</b>
* <b>JavaScript</b>
* <b>Maven</b>

## Features 🎯
<br>


* Self-signed SSL certificate that enables an encrypted connection between a server and a client
* Authorization and authentication mechanism with JWT access and refresh tokens
* Protection against Cross-Site Request Forgery (CSRF) and Cross Site Scripting (XSS)
* In-memory caching data with Redis
* Caching static assets using HTTP caching
* Meter reading service for monthly transmission of meter values
* Payment service without integration with an external payment system
* Calculation service with monthly calculation of payments
* Feedback service for customer feedback
* Email service for system notifications
* Exchange rate service that updates exchange rates daily
* Fuel price service that updates fuel prices daily
* Telegram bot that allows users to perform all operations with their personal account and monitor fuel prices

## Getting Started 🚀
### Installation 🔧

To get started with this Spring Boot project, follow the steps below:
1. Clone the repository :

   ```
   git clone https://github.com/andriymarych/gas-app.git
   ``` 
   or download ZIP file

2. Create `src/main/resources/application.properties` file
3. Configure following properties in `src/main/resources/application.properties` file:
   <br>

   ```
   spring.datasource.url=jdbc:postgresql://postgres:5432/UkrGas
   spring.datasource.username=postgres
   spring.datasource.password=postgres
   spring.jpa.properties.hibernate.default_schema=ukr_gas

   spring.cache.type=redis
   spring.redis.host=redis
   spring.redis.port=6379
   spring.redis.password=password

   #Configure properties with your SMTP server details in
   spring.mail.host=smtp.gmail.com
   spring.mail.post=587
   spring.mail.username=your-username
   spring.mail.password=your-password
   spring.mail.smtp.auth=true
   spring.mail.starttls.enable=true

   server.port=8443
   server.ssl.enabled=true
   server.ssl.keyStore=src/main/resources/ukrGasSSL.p12
   server.ssl.keyStorePassword=password
   server.ssl.keyStoreType=PKCS12
   server.ssl.keyAlias=appsecurity

   #Get a free API key at https://currencyapi.com/ and specify it
   currencyRate.api.key=your-api-key
  
   #Get a free API key at https://collectapi.com/api/gasPrice/gas-prices-api and specify it
   gasPrice.api.key=your-api-key

   application.security.jwt.secret-key=jYE0AR+tp8nZh1wJJMIcTiiCFqIMufPJOz+Dn6GSmAhXapuksK/V8IYjwafV7qEJ
   application.security.jwt.expiration=86400000
   application.security.jwt.refresh-token.expiration=604800000
  
   #To work with the Telegram bot implementation of the service, register a Telegram bot and specify its name and token
   telegram.bot.name=botName
   telegram.bot.token=botToken
  

   ```

### Usage ✅
- [IntelliJ IDEA 💡](#intellij-idea-)
- [Docker 🐳](#docker-)

### IntelliJ IDEA 💡

To get started with this Spring Boot project, follow the steps below:
1. Ensure that you have JDK 17 or later installed on your machine.<br>
   If not, download JDK 17 from  https://www.oracle.com/java/technologies/downloads/#jdk17
2. Install and set up Apache Maven from https://maven.apache.org/download.cgi
3. Install and set up a PostgreSQL database from https://www.postgresql.org/download
4. Import the database in PostgreSQL from `src/main/resources/db/UkrGas.sql` file
5. Change the properties with your PostgreSQL database data in the src/main/resources/application.properties file:
   ```
   spring.datasource.url=your-db-url
   spring.datasource.username=your-db-username
   spring.datasource.password=your-db-password
   spring.jpa.properties.hibernate.default_schema=your-db-scheme
   ```
6. Download and install Redis from https://redis.io/docs/getting-started/installation

7. Change the following line in `src/main/resources/application.properties` file :
   ```
   spring.redis.host=redis
   ```
   to
   ```
   spring.redis.host=localhost
   ```
8. To start the Redis server, run the following command:
   ```
   redis-server     
   ```
9. Open command line, navigate to the root of the spring boot project and
   install the required dependencies by executing following command:
   
   ```
   mvn install
   ```
11. Open command line, navigate to the root of the spring boot project and execute the command:
    ```
    mvn spring-boot:run
    ```
12. In the address bar of the web browser, enter the URL  `https://localhost:8443/`

👤 In order to register a user account, enter the following personal gas account number: 03234124

### Docker 🐳
1. Download and install Docker Desktop https://www.docker.com/products/docker-desktop/
2. Open command line, navigate to the root of the spring boot project and execute the command:

   ```
   docker compose up
   ```
3. In the address bar of the web browser, enter the URL  `https://localhost:8443/`

👤 In order to register a user account, enter the following personal gas account number: 03234124

## Documentation 📄
The OpenAPI project specification is available at the following link https://app.swaggerhub.com/apis/andriymarych/UkrGasAPI/2.0.0
## Screenshots 📷

<br>

<div align="center">

<h2>Telegram Bot</h2>
<br> <br>

![Telegram bot GIF](https://github.com/andriymarych/gas-app/assets/87490528/5e6295de-f21f-4ba5-a9bb-70c5d3af4d2d)

<br> <br>

<h2>Web</h2>

  <br> <br>

![WEB GIF](https://github.com/andriymarych/gas-app/assets/87490528/a3730fa2-79bd-4dcc-9631-6d85452a7bf6)

  <br> <br>

<h2>Email Service</h2>
<br> <br>
<img width="800" alt="Email" src="https://github.com/andriymarych/gas-app/assets/87490528/9775b9bb-72d1-417b-a138-e31af3eec6ef">
<br>

  <br>
Template for reminding users to submit meter readings on time, which is automatically sent by the mail service on the 1st of each month
</div>
<br>

## Contacts ✉️
<br>
<div align="center">

[![](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white)](https://mail.google.com/mail/u/0/?view=cm&fs=1&to=a.marych.ua@gmail.com)
&emsp;
[![](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/andriy-marych/)
&emsp;
[![](https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white)](https://t.me/andriymarych)

</div>

<br>

   
