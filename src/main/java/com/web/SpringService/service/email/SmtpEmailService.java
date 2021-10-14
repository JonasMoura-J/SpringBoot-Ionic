package com.web.SpringService.service.email;

import javax.mail.internet.MimeMessage;

import com.web.SpringService.domain.Pedido;

public class SmtpEmailService extends AbstractEmailService{
	
	@Override
	public void sendOrderConfirmationEmail(String to, String subject, Pedido pedido) {
		//String message = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(to,subject, htmlFromTemplatePedido(pedido));
	}

	@Override
	public void sendHtmlEmail(MimeMessage message) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
//		// TODO Auto-generated method stub
//		
//	}
}