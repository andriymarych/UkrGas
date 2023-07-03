

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
- [Contact](#contacts-)



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
<div align="center">

![GIF](https://github.com/andriymarych/gas-app/assets/87490528/a3730fa2-79bd-4dcc-9631-6d85452a7bf6)

</div>

* User service that implements the authorization process and profile system
* Meter reading service for monthly transmission of meter values
* Payment service without integration with an external payment system
* Calculation service with monthly calculation of payments
* Feedback service for customer feedback
* Email service for system notifications
* Exchange rate service that updates exchange rates daily
* Fuel price service that updates fuel prices daily
* Self-signed SSL certificate that provides access to localhost:8443

## Getting Started 🚀
### Installation 🔧
To get started with this Spring Boot project, follow the steps below:
1. Ensure that you have JDK 17 or later installed on your machine.<br>
   If not, download JDK 17 from  https://www.oracle.com/java/technologies/downloads/#jdk17
2. Install and set up a PostgreSQL database from https://www.postgresql.org/download
3. Import database from [UkrGasDB.sql](https://drive.google.com/uc?export=download&id=1V_tEqKheaO-pZHCMGMEbZqRImS91a_-q) file
4. Install and set up Apache Maven from https://maven.apache.org/download.cgi
5. Clone the repository:

   ```
   git clone https://github.com/andriymarych/gas-app.git
   ```

6. Import the database in PostgreSQL from the following file [ukrGasDB.sql](https://drive.google.com/uc?export=download&id=1V_tEqKheaO-pZHCMGMEbZqRImS91a_-q)
7. Create `src/main/resources/application.properties` file
8. Configure properties with your PostgreSQL database details in `src/main/resources/application.properties` file:

   ```
   spring.datasource.url=your-database-url
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   spring.jpa.properties.hibernate.default_schema=ukr_gas
   ```

9. Configure properties with your SMTP server details in `src/main/resources/application.properties` file:

     ```
     spring.mail.host=smtp.gmail.com
     spring.mail.post=587
     spring.mail.username=your-username
     spring.mail.password=your-password
     spring.mail.smtp.auth=true
     spring.mail.starttls.enable=true
     ```

10. Configure the following properties for the SSL certificate in `src/main/resources/application.properties` file:

     ```
    server.port=8443
    server.ssl.enabled=true
    server.ssl.keyStore=src/main/resources/ukrGasSSL.p12
    server.ssl.keyStorePassword=password
    server.ssl.keyStoreType=PKCS12
    server.ssl.keyAlias=appsecurity
     ```

11. Get a free API key at https://currencyapi.com/ and specify it in `src/main/resources/application.properties` file:

    ```
    currencyRate.api.key=your-api-key
    ```

12. Get a free API key at https://collectapi.com/api/gasPrice/gas-prices-api and specify it
    in `src/main/resources/application.properties` file:

    ```
    gasPrice.api.key=your-api-key
    ```
<br>
In the future, the installation process will be simplified by Docker containerization :whale: 

### Usage ✅

1. Open command line, navigate to the root of the spring boot project and execute the command

   ```
   mvn spring-boot:run
   ```
2. In the address bar of the web browser, enter the URL  `https://localhost:8443/`

## Documentation 📄
The OpenAPI project specification is available at the following link https://app.swaggerhub.com/apis/andriymarych/UkrGasAPI/2.0.0
## Screenshots 📷

<br>

<div align="center">
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

   