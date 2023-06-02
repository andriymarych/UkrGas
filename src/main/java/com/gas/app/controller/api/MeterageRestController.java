package com.gas.app.controller.api;

import com.gas.app.dto.MeterageResponseDto;
import com.gas.app.dto.UserSessionDto;
import com.gas.app.service.MeterageService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/personalAccount/")
public class MeterageRestController {

    private final MeterageService meterageService;
    @GetMapping("/{personalAccountId}/meterage/")
    @Transactional
    public ResponseEntity<Object> getMeterages(@PathVariable Long personalAccountId,
                                               @RequestParam Long userId,
                                               @RequestParam Long authId) {
        UserSessionDto userSessionDto = new UserSessionDto(userId, authId);
        MeterageResponseDto meterageResponseDto  = meterageService
                .getMeterageByPersonalAccountId(userSessionDto, personalAccountId);
        return ResponseHandler.generateResponse("Login is successful",
                HttpStatus.OK, meterageResponseDto);
    }
}
