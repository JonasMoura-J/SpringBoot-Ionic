package com.web.SpringService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.SpringService.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}