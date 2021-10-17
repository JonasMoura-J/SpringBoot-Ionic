package com.web.SpringService.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.SpringService.dto.EmailDTO;
import com.web.SpringService.security.JWTUtil;
import com.web.SpringService.security.UserSpringSecurity;
import com.web.SpringService.service.AuthService;
import com.web.SpringService.service.UserService;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
	
	@Autowired
	JWTUtil jwtUtil;
	
	@Autowired
	AuthService authService;

	@PostMapping
	@RequestMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSpringSecurity user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	@RequestMapping(value = "/forgot")
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objEmail) {
		authService.sendNewPassword(objEmail.getEmail());
		return ResponseEntity.noContent().build();
	}
}
