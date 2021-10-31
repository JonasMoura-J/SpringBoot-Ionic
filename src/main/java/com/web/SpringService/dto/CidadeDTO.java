package com.web.SpringService.dto;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.web.SpringService.domain.Cidade;

public class CidadeDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	public CidadeDTO() {
		
	}

	public CidadeDTO(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	public CidadeDTO(Cidade cidade) {
		super();
		id = cidade.getId();
		nome = cidade.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}