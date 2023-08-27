package com.gas.app.repository.fuel;

import com.gas.app.config.PostgreSQLTestContainerConfiguration;
import com.gas.app.entity.fuel.FuelPrice;
import com.gas.app.entity.fuel.StandardFuelTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(PostgreSQLTestContainerConfiguration.class)
class FuelPriceRepositoryTest {

    @Autowired
    private FuelPriceRepository fuelPriceRepository;

    @Test
    @Transactional
    void shouldReturnFuelPriceByDate() {
        FuelPrice fuelPriceLPG = new FuelPrice(StandardFuelTypeEnum.LPG,0.661,"Ukraine");
        FuelPrice fuelPriceDIESEL = new FuelPrice(StandardFuelTypeEnum.DIESEL,1.352,"Ukraine");
        FuelPrice fuelPriceGAS = new FuelPrice(StandardFuelTypeEnum.GASOLINE,1.331,"Ukraine");

        List<FuelPrice> expectedFuelPrices = List.of(fuelPriceLPG,fuelPriceDIESEL,fuelPriceGAS);

        fuelPriceRepository.saveAll(expectedFuelPrices);

        List<FuelPrice> fuelPriceFound = fuelPriceRepository.findByDate(new Date());

        assertThat(expectedFuelPrices).hasSameElementsAs(fuelPriceFound);
    }

}