package com.web.SpringService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.web.SpringService.domain.Cliente;
import com.web.SpringService.dto.ClienteDTO;
import com.web.SpringService.repositories.ClienteRepository;
import com.web.SpringService.service.exceptions.DataIntegrityException;
import com.web.SpringService.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado " + id + ", Tipo: "
				+ Cliente.class.getName()));
	}
	
	public List<Cliente> findAll(){
		return repository.findAll();
	}

	public Page<Cliente> findPaged(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of (page, linesPerPage,  Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return repository.save(obj);
	}
	
	public void update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		repository.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um Cliente que posssui pedidos");
		}
	}
	
	public Cliente dtoToCategotia(ClienteDTO dto) {
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
