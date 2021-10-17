package com.web.SpringService.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.SpringService.domain.Cliente;
import com.web.SpringService.domain.PagamentoComBoleto;
import com.web.SpringService.domain.Pedido;
import com.web.SpringService.domain.enums.SituacaoPagamento;
import com.web.SpringService.repositories.ItemPedidoRepository;
import com.web.SpringService.repositories.PagamentoRepository;
import com.web.SpringService.repositories.PedidoRepository;
import com.web.SpringService.security.UserSpringSecurity;
import com.web.SpringService.service.email.EmailService;
import com.web.SpringService.service.exceptions.AuthorizationException;
import com.web.SpringService.service.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	PedidoRepository pedidoRepository;
	@Autowired
	PagamentoRepository pagamentoRepository;
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	@Autowired
	BoletoService boletoService;
	@Autowired
	ProdutoService produtoService;
	@Autowired
	ClienteService clienteService;
	@Autowired
	EmailService emailService;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(LocalDateTime.now());
		pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
		
		pedido.getPagamento().setEstado(SituacaoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if(pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamento = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preecherPagamentoComBoleto(pagamento, pedido.getInstante());
		}
		pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		pedido.getItens().stream().forEach(x -> {
			x.setDesconto(0.0);
			x.setProduto(produtoService.buscar(x.getProduto().getId()));
			x.setPreco(x.getProduto().getPreco());
			x.setPedido(pedido);		 
		});
		
		itemPedidoRepository.saveAll(pedido.getItens());
		emailService.sendOrderConfirmationEmail(pedido.getCliente().getEmail(), "Pedido Confirmado", pedido);
		return pedido;
	}
	
	public Page<Pedido> findPaged(Integer page, Integer linesPerPage, String orderBy, String direction){
		UserSpringSecurity user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,  Direction.valueOf(direction), orderBy);
		
		Cliente cliente = clienteService.find(user.getId());
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}
}
