package com.accenture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetPizzeriaApplication {

	private static final Logger logger = LoggerFactory.getLogger(ProjetPizzeriaApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(ProjetPizzeriaApplication.class, args);

		logger.info("Application started");
	}

}
