package com.web.SpringService.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.web.SpringService.domain.Cliente;
import com.web.SpringService.dto.ClienteDTO;
import com.web.SpringService.dto.ClienteNewDto;
import com.web.SpringService.service.ClienteService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value="/clientes")
@Api(value="Api Rest Pedidos")
@CrossOrigin(origins = "*")
public class ClienteController {
	
	@Autowired
	ClienteService service;
	
	@GetMapping
	@RequestMapping(value="/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable Integer id){
		Cliente obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping
	@RequestMapping(value="/email/{email}")
	public ResponseEntity<Cliente> findByEmail(@PathVariable String email){
		Cliente obj = service.findByEmail(email);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll(){
		List<Cliente> Clientes = service.findAll();
		List<ClienteDTO> ClientesDTO = Clientes.stream()
				.map(x -> new ClienteDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(ClientesDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	@RequestMapping(value="/page")
	public ResponseEntity<Page<ClienteDTO>> findPaged(
			@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value="direction", defaultValue = "ASC") String direction){
		
		Page<Cliente> Clientes = service.findPaged(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> ClientesDTO = Clientes.map(x -> new ClienteDTO(x));
		return ResponseEntity.ok().body(ClientesDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDto objDto){
		Cliente obj = service.fromDto(objDto);
		obj = service.insert(obj);
		URI uri =  ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
		Cliente obj = service.fromDto(objDto);
		obj.setId(id);
		service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/picture")
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile file){
		URI uri = service.uplodProfilePicture(file);
		return ResponseEntity.created(uri).build();
	}
}
