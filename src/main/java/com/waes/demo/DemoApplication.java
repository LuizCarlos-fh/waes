package com.waes.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication sa = new SpringApplication(DemoApplication.class);
		sa.run(args);
	}

}
