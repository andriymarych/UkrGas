package com.gas.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calculations")
@RequiredArgsConstructor
public class CalculationsController {

    @GetMapping
    public String getPage() {
        return "calculations";
    }
}