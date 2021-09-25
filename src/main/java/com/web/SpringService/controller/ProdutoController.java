package com.web.SpringService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.SpringService.domain.Categoria;
import com.web.SpringService.domain.Produto;
import com.web.SpringService.dto.CategoriaDTO;
import com.web.SpringService.dto.ProdutoDTO;
import com.web.SpringService.service.ProdutoService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value="/produtos")
@Api(value="Api Rest Produto")
@CrossOrigin(origins = "*")
public class ProdutoController {
	
	@Autowired
	private ProdutoService service;
	
	@GetMapping
	@RequestMapping(value="/{id}")
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> findPaged(
			@RequestParam(value="nome", defaultValue = "") Integer nome,
			@RequestParam(value="categorias", defaultValue = "") Integer categorias,
			@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value="direction", defaultValue = "ASC") String direction){
		
		Page<Categoria> categorias = service.findPaged(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> categoriasDTO = categorias.map(x -> new CategoriaDTO(x));
		return ResponseEntity.ok().body(categoriasDTO);
	}
}

