package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DemoApplication {

	public static void main(String[] args) {

		System.setProperty("spring.devtools.restart.enabled", "true");
		System.setProperty("spring.thymeleaf.cache", "false");

		SpringApplication.run(DemoApplication.class, args);
	}

}
