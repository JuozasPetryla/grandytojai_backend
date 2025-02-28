package com.grandytojai.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BackendApplication {
	public static final String PATH = "/api";

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
