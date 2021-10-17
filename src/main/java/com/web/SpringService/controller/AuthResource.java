package com.web.SpringService.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.SpringService.security.JWTUtil;
import com.web.SpringService.security.UserSpringSecurity;
import com.web.SpringService.service.UserService;

@RestController
@RequestMapping(path = "/auth")
public class AuthResource {
	
	@Autowired
	JWTUtil jwtUtil;

	@GetMapping
	@RequestMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSpringSecurity user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
}
