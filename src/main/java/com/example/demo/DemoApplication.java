package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.security.Principal;

@SpringBootApplication
@EnableWebSecurity
@RestController
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

	@GetMapping("/User")
	public Principal user(Principal user){
		return user;
	}

}
