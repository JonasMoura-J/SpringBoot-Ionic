package com.web.SpringService.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.web.SpringService.domain.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Integer>{
	
	@Transactional(readOnly = true)
	public List<Estado> findAllByOrderByNome();
}
