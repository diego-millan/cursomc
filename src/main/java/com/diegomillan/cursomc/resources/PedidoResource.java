package com.diegomillan.cursomc.resources;

import com.diegomillan.cursomc.domain.Pedido;
import com.diegomillan.cursomc.resources.utils.URL;
import com.diegomillan.cursomc.services.PedidoService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) throws ObjectNotFoundException {
		
		Pedido pedido = service.find(id);
		return ResponseEntity.ok().body(pedido);
	}

	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido) throws ObjectNotFoundException {
		pedido = service.insert(pedido);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(pedido.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}
}
