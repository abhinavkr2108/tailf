package com.example.logdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LogdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogdemoApplication.class, args);
		System.out.println("Hello World!");
	}

}
