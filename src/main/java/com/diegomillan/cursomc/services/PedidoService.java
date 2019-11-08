package com.diegomillan.cursomc.services;

import com.diegomillan.cursomc.domain.*;
import com.diegomillan.cursomc.domain.enums.EstadoPagamento;
import com.diegomillan.cursomc.repositories.ItemPedidoRepository;
import com.diegomillan.cursomc.repositories.PagamentoRepository;
import com.diegomillan.cursomc.repositories.PedidoRepository;
import com.diegomillan.cursomc.repositories.ProdutoRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;


@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido find(Integer id) throws ObjectNotFoundException  {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(pedidoNotFoundException(id)));
	}

	private String pedidoNotFoundException(Integer id) {
		return "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName();
	}

    public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);

		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamentoComBoleto = ((PagamentoComBoleto) pedido.getPagamento());
			boletoService.preencherPagamentoComBoleto(pagamentoComBoleto, pedido.getInstante());
		}

		pedido = repo.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());

		insertItemPedido(pedido);

		return pedido;
    }

	private void insertItemPedido(Pedido pedido) {
		for (ItemPedido itemPedido : pedido.getItens()) {
			itemPedido.setDesconto(0d);
			 if(produtoRepository.findById(itemPedido.getProduto().getId()).isPresent()) {
				 Produto produto = produtoRepository.findById(itemPedido.getProduto().getId()).get();
				 itemPedido.setPreco(produto.getPreco());
				 itemPedido.setPedido(pedido);
			 }
		}

		itemPedidoRepository.saveAll(pedido.getItens());
	}
}
