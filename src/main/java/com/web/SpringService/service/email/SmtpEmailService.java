package com.web.SpringService.service.email;

import com.web.SpringService.domain.Pedido;

public class SmtpEmailService extends AbstractEmailService{

	public void sendOrderConfirmationEmail(String to, String subject, Pedido pedido) {
		String message = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(to,subject, message);
	}	
}