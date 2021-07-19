package com.web.SpringService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.SpringService.domain.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

}
