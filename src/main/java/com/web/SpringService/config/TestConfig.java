package com.web.SpringService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.web.SpringService.service.DBService;
import com.web.SpringService.service.email.EmailService;
import com.web.SpringService.service.email.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	DBService dbService;
	
	@Bean
	public boolean instatiateDataBase() {
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}