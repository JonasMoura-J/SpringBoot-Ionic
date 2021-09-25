package com.web.SpringService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.web.SpringService.domain.Categoria;
import com.web.SpringService.domain.Pedido;
import com.web.SpringService.domain.Produto;
import com.web.SpringService.repositories.CategoriaRepository;
import com.web.SpringService.repositories.ProdutoRepository;
import com.web.SpringService.service.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	ProdutoRepository repo;
	@Autowired
	CategoriaRepository categoriasRepository;
	
	public Produto buscar(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of (page, linesPerPage,  Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriasRepository.findAllById(ids);
		return repo.search(nome, categorias, pageRequest);
		
	}
}
