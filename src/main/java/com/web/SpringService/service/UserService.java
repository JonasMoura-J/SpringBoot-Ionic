package com.web.SpringService.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.web.SpringService.security.UserSpringSecurity;

public class UserService {
	
	public static UserSpringSecurity autenticated() {
		try {
			return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	
}
