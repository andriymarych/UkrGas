package com.gas.app.controller.api.fuel;

import com.gas.app.dto.fuel.FuelPriceChangeDto;
import com.gas.app.entity.currency.StandardCurrencyEnum;
import com.gas.app.service.fuel.FuelPriceService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/fuel-prices")
public class FuelPriceRestController {

    private final FuelPriceService fuelPriceService;

    @GetMapping
    public ResponseEntity<Object> getFuelPrices(@RequestParam String currency){

        List<FuelPriceChangeDto> fuelPriceChangeDtoList = fuelPriceService.getFuelPrices(StandardCurrencyEnum.valueOf(currency));
        return ResponseHandler.generateResponse("OK", HttpStatus.OK, fuelPriceChangeDtoList);

    }
}
