package com.gas.app;

import com.gas.app.config.PostgreSQLTestContainerConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(PostgreSQLTestContainerConfiguration.class)
class GasApplicationTests {

	@Test
	void contextLoads() {
	}

}
