package com.diegomillan.cursomc.resources;

import com.diegomillan.cursomc.CategoriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.diegomillan.cursomc.domain.Categoria;
import com.diegomillan.cursomc.services.CategoriaService;

import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) throws ObjectNotFoundException {
		
		Categoria categoria = service.find(id);
		return ResponseEntity.ok().body(categoria);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria categoria) {
		ResponseEntity responseEntity;
		categoria = service.insert(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
				buildAndExpand(categoria.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria categoria, @PathVariable Integer id)
			throws  ObjectNotFoundException {

		categoria.setId(id);
		categoria = service.update(categoria);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping( method=RequestMethod.GET)
	public ResponseEntity<?> findAll()  {
		List<Categoria> categorias = service.findAll();

		List<CategoriaDTO> categoriasDTO = categorias.stream().map(
				categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());

		return ResponseEntity.ok().body(categoriasDTO);
	}
}
