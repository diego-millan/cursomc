package com.diegomillan.cursomc.services;

import com.diegomillan.cursomc.domain.Categoria;
import com.diegomillan.cursomc.repositories.CategoriaRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoriaServiceTest {

    @MockBean
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaService categoriaService;

    @Test
    public void givenValidCategoriaIdWhenFindIdInDatabaseThenReturnCategoria() {
        Categoria categoria = new Categoria(1, "Informática");
        Mockito.when(categoriaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(categoria));

        try {
            Categoria categoriaFound = categoriaService.find(Mockito.anyInt());

            Assert.assertSame(categoria, categoriaFound);

        } catch (ObjectNotFoundException e) {
            Assert.fail();
        }
    }

    @Test
    public void givenNewCategoriaWhenInsertThenReturnSame() {
        Categoria categoria = new Categoria (1,"Informática");
        Mockito.when(categoriaRepository.save(categoria)).thenReturn(categoria);
        Categoria categoriaInsert = categoriaService.insert(categoria);

        Assert.assertSame(categoria, categoriaInsert);
    }
}
