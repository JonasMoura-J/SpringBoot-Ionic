package com.web.SpringService.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido){
		pedido = service.insert(pedido);
		URI uri =  ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping
	public ResponseEntity<Page<Pedido>> findPaged(
			@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue = "instante") String orderBy,
			@RequestParam(value="direction", defaultValue = "DESC") String direction){
		
		Page<Pedido> pedidos = service.findPaged(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(pedidos);
	}
}

