package com.web.SpringService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort.Direction;
import com.web.SpringService.domain.Categoria;
import com.web.SpringService.dto.CategoriaDTO;
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
	
	public List<Categoria> findAll(){
		return repository.findAll();
	}
	
	//page = qual página que eu quero
	//linesPerPage =quantas linhas por páginas
	//orderBy = por qual atributo do objeto eu quero ordenar?
	//direction = ascendente ou descendente
	public Page<Categoria> findPaged(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of (page, linesPerPage,  Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
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
	
	public Categoria dtoToCategotia(CategoriaDTO dto) {
		return new Categoria(dto.getId(), dto.getNome());
	}
}
