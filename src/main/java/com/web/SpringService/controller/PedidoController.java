package com.web.SpringService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.SpringService.domain.Pedido;
import com.web.SpringService.service.PedidoService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value="/pedidos")
@Api(value="Api Rest Pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {
	
	@Autowired
	private PedidoService service;
	
	@GetMapping
	@RequestMapping(value="/{id}")
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
}

