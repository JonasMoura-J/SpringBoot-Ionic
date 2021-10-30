package com.web.SpringService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringServiceApplication implements CommandLineRunner{
	
	public static void main(String[] args) {	
		SpringApplication.run(SpringServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
