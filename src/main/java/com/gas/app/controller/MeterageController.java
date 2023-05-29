package com.gas.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/meterage")
@RequiredArgsConstructor
public class MeterageController {

    @GetMapping
    public String getPage() {
        return "meterage";
    }
}
