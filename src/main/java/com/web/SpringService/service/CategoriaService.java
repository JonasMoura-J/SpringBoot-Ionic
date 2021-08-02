package com.web.SpringService.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.web.SpringService.domain.Categoria;
import com.web.SpringService.repositories.CategoriaRepository;
import com.web.SpringService.service.exceptions.DataIntegrityException;
import com.web.SpringService.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repository.findById(id);

			return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado " + id + ", Tipo: "
					+ Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repository.save(obj);
	}
	
	public void update(Categoria obj) {
		find(obj.getId());
		repository.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que posssui produtors");
		}
	}
}
