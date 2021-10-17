package com.web.SpringService.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.SpringService.domain.Cliente;
import com.web.SpringService.repositories.ClienteRepository;
import com.web.SpringService.service.email.EmailService;
import com.web.SpringService.service.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private Random random = new Random();
	
	@Autowired
	EmailService emailService;

	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if(cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(bCryptPasswordEncoder.encode(newPass));
		
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente.getEmail(), newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		if(opt == 0) {
			return (char) (random.nextInt(10) + 48);
		}else if(opt == 1) {
			return (char) (random.nextInt(26) + 65);
		}else {
			return (char) (random.nextInt(26) + 97);
		}
	}
}
