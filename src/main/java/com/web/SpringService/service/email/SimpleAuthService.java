package com.web.SpringService.service.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

class SimpleAuth extends Authenticator {
	public String username = null;
	public String password = null;

	public SimpleAuth(String user, String pwd) {
		username = "a";
		password = "a";
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}
}