package com.diegomillan.cursomc;

import com.diegomillan.cursomc.domain.Categoria;
import com.diegomillan.cursomc.repositories.CategoriaRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void givenValidCategoriaNameWhenFindInDatabaseThenReturnCategoria() {
        //Categoria categoria = testEntityManager.persistFlushFind(new Categoria(null,"Informática"));
        Categoria categoriaFound = categoriaRepository.findById(1).get();

        Assert.assertEquals("Informática",categoriaFound.getNome());
    }

    @Test
    public void givenValidCategoriaIdWhenFindInDatabaseThenReturnCategoria() {
        Categoria categoria = testEntityManager.persistFlushFind(new Categoria(null,"Geek"));
        Categoria categoriaFound = categoriaRepository.findById(8).get();

        Assert.assertEquals(categoria,categoriaFound);
    }
}
