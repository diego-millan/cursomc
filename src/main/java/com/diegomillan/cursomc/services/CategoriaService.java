package com.diegomillan.cursomc.services;

import com.diegomillan.cursomc.domain.Categoria;
import com.diegomillan.cursomc.dto.CategoriaDTO;
import com.diegomillan.cursomc.repositories.CategoriaRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoriaService implements ICategoriaService{
	
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

	public Categoria update(Categoria categoria) throws ObjectNotFoundException  {
		Categoria categoriaAlteracao = find(categoria.getId());
		updateData(categoriaAlteracao, categoria);
		return repo.save(categoriaAlteracao);
	}

	public void delete(Integer id) throws ObjectNotFoundException {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(dataIntegrityException());
		}
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}

	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest) ;
	}

	private String dataIntegrityException() {
		return "Não é possível excluir uma categoria que possui produtos";
	}

	private String categoriaNotFoundException(Integer id) {
		return "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName();
	}

	public Categoria fromDTO(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}

	private void updateData(Categoria categoriaAlteracao, Categoria categoria) {
		categoriaAlteracao.setNome(categoria.getNome());
	}

}
