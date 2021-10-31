package com.web.SpringService.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.SpringService.domain.Cidade;
import com.web.SpringService.domain.Estado;
import com.web.SpringService.dto.CidadeDTO;
import com.web.SpringService.dto.EstadoDTO;
import com.web.SpringService.service.CidadeService;
import com.web.SpringService.service.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoController {
	
	@Autowired
	EstadoService estadoService;
	
	@Autowired
	CidadeService cidadeService;
	
	@GetMapping
	public ResponseEntity<List<EstadoDTO>> findAll(){
		List<Estado> estados = estadoService.findAll();
		List<EstadoDTO> estadosDTO = estados.stream()
				.map(x -> new EstadoDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(estadosDTO);
	}
	
	@GetMapping
	@RequestMapping(value="/{estadoId}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId){
		List<Cidade> cidade = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> cidadesDTO = cidade.stream()
				.map(x -> new CidadeDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(cidadesDTO);
	}
}
