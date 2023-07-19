package com.gas.app.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/meter-reading")
@RequiredArgsConstructor
public class MeterReadingController {


    @GetMapping
    public String getPage() {
        return "meter-reading";
    }
}
