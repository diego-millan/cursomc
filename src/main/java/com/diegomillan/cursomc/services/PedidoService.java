package com.diegomillan.cursomc.services;

import com.diegomillan.cursomc.domain.Cliente;
import com.diegomillan.cursomc.domain.ItemPedido;
import com.diegomillan.cursomc.domain.PagamentoComBoleto;
import com.diegomillan.cursomc.domain.Pedido;
import com.diegomillan.cursomc.domain.enums.EstadoPagamento;
import com.diegomillan.cursomc.repositories.ItemPedidoRepository;
import com.diegomillan.cursomc.repositories.PagamentoRepository;
import com.diegomillan.cursomc.repositories.PedidoRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ClienteService clienteService;

	public Pedido find(Integer id) throws ObjectNotFoundException  {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(pedidoNotFoundException(id)));
	}

	private String pedidoNotFoundException(Integer id) {
		return "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName();
	}

    public Pedido insert(Pedido pedido) throws ObjectNotFoundException {
		pedido.setId(null);
		pedido.setInstante(new Date());

		Cliente cliente = clienteService.find(pedido.getCliente().getId());
		pedido.setCliente(cliente);

		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);

		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamentoComBoleto = ((PagamentoComBoleto) pedido.getPagamento());
			boletoService.preencherPagamentoComBoleto(pagamentoComBoleto, pedido.getInstante());
		}

		pedido = repo.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());

		insertItemPedido(pedido);
		System.out.println(pedido);
		return pedido;
    }

	private void insertItemPedido(Pedido pedido) throws ObjectNotFoundException {
		for (ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		}

		itemPedidoRepository.saveAll(pedido.getItens());
	}
}
