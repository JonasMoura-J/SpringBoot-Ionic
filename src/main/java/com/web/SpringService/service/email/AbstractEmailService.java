package com.web.SpringService.service.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.web.SpringService.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SmtpEmailService.class);

	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	TemplateEngine engine;

	private String mailSMTPServer;
	private String mailSMTPServerPort;

	public AbstractEmailService() {
		mailSMTPServer = "smtp.gmail.com";
		mailSMTPServerPort = "465";
	}

	@Override
	public void sendOrderConfirmationEmail(String to, String subject, Pedido pedido) {
		sendEmail(to,subject, htmlFromTemplatePedido(pedido));
	}

	public String getMailSMTPServer() {
		return mailSMTPServer;
	}

	public void setMailSMTPServer(String mailSMTPServer) {
		this.mailSMTPServer = mailSMTPServer;
	}

	public String getMailSMTPServerPort() {
		return mailSMTPServerPort;
	}

	public void setMailSMTPServerPort(String mailSMTPServerPort) {
		this.mailSMTPServerPort = mailSMTPServerPort;
	}
	
	@Override
	public void sendEmail(String to, String subject, String message) {

		LOGGER.info("Enviando Email.. ");

		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", getMailSMTPServer());
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.user", "agendadorferias@gmail.com");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", getMailSMTPServerPort());
		props.put("mail.smtp.socketFactory.port", getMailSMTPServerPort());
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		SimpleAuth auth = null;
		auth = new SimpleAuth("a", "a");

		Session session = Session.getDefaultInstance(props, auth);
		session.setDebug(true);

		Message msg = new MimeMessage(session);

		try {
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setFrom(new InternetAddress(sender));
			msg.setSubject(subject);
			msg.setContent(message, "text/html");

		} catch (Exception e) {
			System.out.println(">> Erro: Completar Mensagem");
			e.printStackTrace();
		}

		Transport tr;
		try {
			tr = session.getTransport("smtp");
			tr.connect(getMailSMTPServer(), "agendadorferias@gmail.com", "suasenha123");
			msg.saveChanges();
			tr.sendMessage(msg, msg.getAllRecipients());
			tr.close();
		} catch (Exception e) {
			System.out.println(">> Erro: Envio Mensagem");
			e.printStackTrace();
		}

		LOGGER.info("Email enviado!");
	}
	
	protected String htmlFromTemplatePedido(Pedido pedido) {
		Context context = new Context();
		context.setVariable("pedido", pedido);
		return engine.process("email/confirmacaoPedido", context);
	}
}
