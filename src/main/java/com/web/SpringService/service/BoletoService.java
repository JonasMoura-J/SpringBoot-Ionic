package com.web.SpringService.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.web.SpringService.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	
	public void preecherPagamentoComBoleto(PagamentoComBoleto pagamento, LocalDateTime instantePedido) {
		LocalDateTime dataPag = instantePedido.plusDays(7);
		pagamento.setDataVencimento(dataPag);
	}
}
