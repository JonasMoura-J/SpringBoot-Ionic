package com.web.SpringService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.web.SpringService.service.S3Service;

@SpringBootApplication
public class SpringServiceApplication implements CommandLineRunner{

	@Autowired
	S3Service s3Service;
	
	public static void main(String[] args) {	
		SpringApplication.run(SpringServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		s3Service.uplodFile("C:\\temp\\Curriculo.pdf");
	}
}
