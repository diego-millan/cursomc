package com.diegomillan.cursomc.resources;

import com.diegomillan.cursomc.domain.Produto;
import com.diegomillan.cursomc.dto.ProdutoDTO;
import com.diegomillan.cursomc.resources.utils.URL;
import com.diegomillan.cursomc.services.ProdutoService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) throws ObjectNotFoundException {

		Produto produto = service.find(id);
		return ResponseEntity.ok().body(produto);
	}

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction)  {
		List<Integer> categoriaIds = URL.decodeInList(categorias);
		String nomeDecoded = URL.decodeParam(nome);
		Page<Produto> produtos = service.search(nomeDecoded, categoriaIds, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> produtoDTOList = produtos.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(produtoDTOList);
	}
}
