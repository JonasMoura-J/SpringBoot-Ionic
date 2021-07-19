package com.web.SpringService.domain;

import java.time.LocalDate;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.web.SpringService.domain.enums.SituacaoPagamento;

@Entity
public class PagamentoComBoleto extends Pagamento {
	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dataVencimento;

	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dataPagamento;

	public PagamentoComBoleto() {
	}

	public PagamentoComBoleto(Integer id, SituacaoPagamento estado, Pedido pedido, LocalDate dataVencimento, LocalDate dataPagamento) {
		super(id, estado, pedido);
		this.dataPagamento = dataPagamento;
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}	
}
