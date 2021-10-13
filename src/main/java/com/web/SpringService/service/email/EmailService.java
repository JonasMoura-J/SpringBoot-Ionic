package com.web.SpringService.service.email;

public interface EmailService {
	
	void sendOrderConfirmationEmail(String to, String subject, String msg);
	
	void sendEmail(String to, String subject, String message);
}
