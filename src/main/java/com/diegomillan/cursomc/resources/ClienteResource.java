package com.diegomillan.cursomc.resources;

import com.diegomillan.cursomc.domain.Cliente;
import com.diegomillan.cursomc.dto.ClienteDTO;
import com.diegomillan.cursomc.services.ClienteService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) throws ObjectNotFoundException {
		
		Cliente cliente = service.find(id);
		return ResponseEntity.ok().body(cliente);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteDTO clienteDTO) {

		ResponseEntity responseEntity;
		Cliente cliente = service.fromDTO(clienteDTO);

		cliente = service.insert(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
				buildAndExpand(cliente.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id)
			throws  ObjectNotFoundException {

		Cliente cliente = service.fromDTO(clienteDTO);
		cliente.setId(id);
		cliente = service.update(cliente);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping( method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll()  {
		List<Cliente> clientes = service.findAll();

		List<ClienteDTO> clientesDTO = clientes.stream().map(
				cliente -> new ClienteDTO(cliente)).collect(Collectors.toList());

		return ResponseEntity.ok().body(clientesDTO);
	}

	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction)  {

		Page<Cliente> clientes = service.findPage(page, linesPerPage, orderBy, direction);

		Page<ClienteDTO> clientesDTO = clientes.map(
				cliente -> new ClienteDTO(cliente));

		return ResponseEntity.ok().body(clientesDTO);
	}
}
