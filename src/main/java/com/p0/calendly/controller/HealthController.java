package com.p0.calendly.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/")
    public String checkHealth() {
        return "Server is up and running!";
    }
}
