package com.gas.app.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class PostgreSQLTestContainerConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgreSQLContainer(){

        return new PostgreSQLContainer<>("postgres:14.5")
                .withDatabaseName("UkrGas")
                .withUsername("postgres")
                .withPassword("postgres");
    }

}
