package com.diegomillan.cursomc.services;

import com.diegomillan.cursomc.domain.Categoria;
import com.diegomillan.cursomc.domain.Pedido;
import com.diegomillan.cursomc.repositories.PedidoRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	public Pedido find(Integer id) throws ObjectNotFoundException  {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(pedidoNotFoundException(id)));
	}

	private String pedidoNotFoundException(Integer id) {
		return "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName();
	}
}
