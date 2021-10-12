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
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(LocalDateTime.now());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		
		obj.getPagamento().setEstado(SituacaoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamento = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preecherPagamentoComBoleto(pagamento, obj.getInstante());
		}
		repository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		obj.getItens().stream().forEach(x -> {
			x.setDesconto(0.0);
			x.setProduto(produtoService.buscar(x.getProduto().getId()));
			x.setPreco(x.getProduto().getPreco());
			x.setPedido(obj);		 
		});
		
		itemPedidoRepository.saveAll(obj.getItens());
		System.out.println(obj);
		return obj;
	}
}
