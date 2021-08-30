package com.web.SpringService.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.web.SpringService.service.validation.ClienteInsert;

@ClienteInsert
public class ClienteNewDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Preenchimento obrigatório")
	@Length(min=5, max=120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;
	
	@NotNull(message = "Preenchimento obrigatório")
	@Email(message="email inválido")
	private String email;
	
	@NotNull(message = "Preenchimento obrigatório")
	private String cpfOuCnpj;
	private Integer tipo;
	
	@NotNull(message = "Preenchimento obrigatório")
	private String logradouro;
	@NotNull(message = "Preenchimento obrigatório")
	private String numero;
	private String complemento;
	private String bairro;
	@NotNull(message = "Preenchimento obrigatório")
	private String cep;
	
	@NotNull(message = "Preenchimento obrigatório")
	private String telefone1;
	private String telefone2;
	private Integer cidadeId;
	
	public ClienteNewDto() {
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public Integer getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}
}
