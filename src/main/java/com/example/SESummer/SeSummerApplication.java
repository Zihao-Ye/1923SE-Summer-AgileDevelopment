package com.example.SESummer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SeSummerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SeSummerApplication.class, args);
	}

}
