package com.web.SpringService.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.web.SpringService.controller.exception.FieldMessage;
import com.web.SpringService.domain.Cliente;
import com.web.SpringService.dto.ClienteDTO;
import com.web.SpringService.repositories.ClienteRepository;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO>{
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ClienteRepository repository;
	
	@Override
	public void initialize(ClienteUpdate cliente) {
		
	}
	
	@Override
	public boolean isValid(ClienteDTO cliente, ConstraintValidatorContext context) {
		List<FieldMessage> exceptions = new ArrayList<>();
		
		verificarEmailDuplicado(cliente, exceptions);
		
		for (FieldMessage fieldMessage : exceptions) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMessage()).addPropertyNode(fieldMessage.getFieldName())
				.addConstraintViolation();
		}
	
		return exceptions.isEmpty();
	}
	
	public void verificarEmailDuplicado(ClienteDTO cliente, List<FieldMessage> exceptions) {
		@SuppressWarnings("unchecked")
		Map<String, String> requestObject = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(requestObject.get("id"));
		
		Cliente validacaoEmail = repository.findByEmail(cliente.getEmail());
		if(validacaoEmail != null && !validacaoEmail.getId().equals(uriId)) {
			exceptions.add(new FieldMessage("email", "Email em uso"));
		}
	}
	
}
