package com.web.SpringService.domain.enums;

public enum SituacaoPagamento {
	PENDENTE(1, "Pendente"),
	QUITADO(2, "Pendente"),
	CANCELADO(3, "Pendente");
	
	private int cod;
	private String descricao;
	
	private SituacaoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static SituacaoPagamento toEnum(Integer cod) {
		if(cod == null) return null;
		
		return values()[cod-1];
	}
}