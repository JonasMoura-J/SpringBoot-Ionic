package com.web.SpringService.service.email;

import org.springframework.mail.SimpleMailMessage;

import com.web.SpringService.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
}
