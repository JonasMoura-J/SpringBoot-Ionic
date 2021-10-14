package com.web.SpringService.service.email;

import javax.mail.internet.MimeMessage;

import com.web.SpringService.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(String to, String subject, Pedido pedido);
	
	void sendEmail(String to, String subject, String message);
	
	void sendHtmlEmail(MimeMessage message);
}