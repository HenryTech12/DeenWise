package com.deenwise.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DeenWiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeenWiseApplication.class, args);
	}

}
