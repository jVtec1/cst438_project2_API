package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @GetMapping("/")
    public String index() {
        return "SPRING BOOT IS WORKING!";
    }

    @GetMapping("/Greeting")
    public String anakin() {
        return "Hello World! I am Spring Boot! I am your brother anakin";
    }
}
