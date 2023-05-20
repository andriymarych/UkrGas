package com.gas.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@EnableCaching
@SpringBootApplication
public class GasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GasApplication.class, args);
	}

}
