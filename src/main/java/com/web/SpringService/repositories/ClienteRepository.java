package com.web.SpringService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.SpringService.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
}
