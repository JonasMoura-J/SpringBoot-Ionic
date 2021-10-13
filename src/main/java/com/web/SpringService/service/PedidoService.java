package com.web.SpringService.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.SpringService.domain.PagamentoComBoleto;
import com.web.SpringService.domain.Pedido;
import com.web.SpringService.domain.enums.SituacaoPagamento;
import com.web.SpringService.repositories.ItemPedidoRepository;
import com.web.SpringService.repositories.PagamentoRepository;
import com.web.SpringService.repositories.PedidoRepository;
import com.web.SpringService.service.email.EmailService;
import com.web.SpringService.service.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	PedidoRepository repository;
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
		Optional<Pedido> pedido = repository.findById(id);
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
		repository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		pedido.getItens().stream().forEach(x -> {
			x.setDesconto(0.0);
			x.setProduto(produtoService.buscar(x.getProduto().getId()));
			x.setPreco(x.getProduto().getPreco());
			x.setPedido(pedido);		 
		});
		
		itemPedidoRepository.saveAll(pedido.getItens());
		emailService.sendOrderConfirmationEmail(pedido.getCliente().getEmail(), "Pedido Confirmado", pedido.toString());
		return pedido;
	}
}
