package com.web.SpringService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.SpringService.domain.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

}
