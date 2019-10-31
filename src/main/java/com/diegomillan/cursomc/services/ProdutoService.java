package com.diegomillan.cursomc.services;

import com.diegomillan.cursomc.domain.Categoria;
import com.diegomillan.cursomc.domain.Produto;
import com.diegomillan.cursomc.repositories.CategoriaRepository;
import com.diegomillan.cursomc.repositories.ProdutoRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id) throws ObjectNotFoundException  {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(produtoNotFoundException(id)));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){

		PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);

		List<Categoria> categorias = categoriaRepository.findAllById(ids);

		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}

	private String produtoNotFoundException(Integer id) {
		return "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName();
	}
}
