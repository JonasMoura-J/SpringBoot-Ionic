package com.web.SpringService.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.web.SpringService.controller.exception.FieldMessage;
import com.web.SpringService.domain.Cliente;
import com.web.SpringService.domain.enums.TipoCliente;
import com.web.SpringService.dto.ClienteNewDto;
import com.web.SpringService.repositories.ClienteRepository;
import com.web.SpringService.service.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDto>{
	
	@Autowired
	ClienteRepository repository;
	
	@Override
	public void initialize(ClienteInsert cliente) {
		
	}
	
	@Override
	public boolean isValid(ClienteNewDto cliente, ConstraintValidatorContext context) {
		List<FieldMessage> exceptions = new ArrayList<>();
		
		validarCpf(cliente, exceptions);
		validarCnpj(cliente, exceptions);
		
		Cliente validacaoEmail = repository.findByEmail(cliente.getEmail());
		if(validacaoEmail != null) {
			exceptions.add(new FieldMessage("email", "Email em uso"));
		}
		
		for (FieldMessage fieldMessage : exceptions) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMessage()).addPropertyNode(fieldMessage.getFieldName())
				.addConstraintViolation();
		}
	
		return exceptions.isEmpty();
	}
	
	public void validarCpf(ClienteNewDto cliente, List<FieldMessage> exceptions) {
		if(cliente.getTipo().equals(TipoCliente.PESSOA_FISICA.getCod())
				&& !BR.isValidCpf(cliente.getCpfOuCnpj())) {
			exceptions.add(new FieldMessage("cpfOuCnpj", "CPF inválido."));
		}
	}
	
	public void validarCnpj(ClienteNewDto cliente, List<FieldMessage> exceptions) {
		if(cliente.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCod())
				&& !BR.isValidCnpj(cliente.getCpfOuCnpj())) {
			exceptions.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido."));
		}
	}
	
}
