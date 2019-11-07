package com.diegomillan.cursomc.services;

import com.diegomillan.cursomc.domain.Categoria;
import com.diegomillan.cursomc.dto.CategoriaDTO;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoriaService {
    Categoria find(Integer id)  throws ObjectNotFoundException;

    Categoria fromDTO(CategoriaDTO categoriaDTO)    ;

    Categoria insert(Categoria categoria)    ;

    Categoria update(Categoria categoria)   throws ObjectNotFoundException  ;

    void delete(Integer id)  throws ObjectNotFoundException  ;

    List<Categoria> findAll()    ;

    Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction);

}
