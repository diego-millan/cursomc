package com.diegomillan.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diegomillan.cursomc.domain.Categoria;
import com.diegomillan.cursomc.repositories.CategoriaRepository;

import javassist.tools.rmi.ObjectNotFoundException;


@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) throws ObjectNotFoundException  {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(categoriaNotFoundException(id)));
	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return repo.save(categoria);
	}

	private String categoriaNotFoundException(Integer id) {
		return "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName();
	}
}
