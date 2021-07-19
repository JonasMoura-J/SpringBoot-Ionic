package com.web.SpringService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.SpringService.domain.Cliente;
import com.web.SpringService.service.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteController {
	
	@Autowired
	ClienteService service;
	
	@GetMapping
	@RequestMapping(value="/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id){
		Cliente obj = service.buscar(id);
		
		return ResponseEntity.ok().body(obj);
	}
}
