package com.web.SpringService.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.web.SpringService.domain.Cidade;
import com.web.SpringService.domain.Cliente;
import com.web.SpringService.domain.Endereco;
import com.web.SpringService.domain.enums.Perfil;
import com.web.SpringService.domain.enums.TipoCliente;
import com.web.SpringService.dto.ClienteDTO;
import com.web.SpringService.dto.ClienteNewDto;
import com.web.SpringService.repositories.ClienteRepository;
import com.web.SpringService.repositories.EnderecoRepository;
import com.web.SpringService.security.UserSpringSecurity;
import com.web.SpringService.service.exceptions.AuthorizationException;
import com.web.SpringService.service.exceptions.DataIntegrityException;
import com.web.SpringService.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	S3Service s3Service;

	public Cliente find(Integer id) {
		UserSpringSecurity user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
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
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
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
	
	public Cliente fromDto(ClienteDTO dto) {
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null, null);
	}
	
	public Cliente fromDto(ClienteNewDto dto) {
		Cliente cliente = new Cliente(null, dto.getNome(), dto.getEmail(), dto.getCpfOuCnpj(), TipoCliente.toEnum(dto.getTipo()), 
				bCryptPasswordEncoder.encode(dto.getSenha()));
		
		Cidade cidade = new Cidade(dto.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getBairro(), dto.getCep(), cliente, cidade);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(dto.getTelefone1());
		
		if(dto.getTelefone2() != null) {
			cliente.getTelefones().add(dto.getTelefone2());
		}
		return cliente; 
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI uplodProfilePicture(MultipartFile file) {
		return s3Service.uplodFile(file);
	}
}
