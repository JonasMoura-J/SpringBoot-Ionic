package com.web.SpringService.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class EmailDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Preenchimento obrigatório")
	@Email(message="email inválido")
	private String email;
	
	public EmailDTO() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
