package com.gas.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/meter-reading")
@RequiredArgsConstructor
public class MeterReadingController {


    @GetMapping
    public String getPage() {
        return "meter-reading";
    }
}
