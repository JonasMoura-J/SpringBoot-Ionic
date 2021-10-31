package com.web.SpringService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.SpringService.domain.Cidade;
import com.web.SpringService.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	CidadeRepository cidadeRepository;
	
	public List<Cidade> findByEstado(Integer estadoId){
		return cidadeRepository.findCidades(estadoId);
	}
}
