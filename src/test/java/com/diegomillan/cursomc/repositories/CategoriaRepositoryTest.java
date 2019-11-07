package com.diegomillan.cursomc.repositories;

import com.diegomillan.cursomc.domain.Categoria;
import com.diegomillan.cursomc.repositories.CategoriaRepository;
import javassist.tools.rmi.ObjectNotFoundException;
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
    public void givenValidCategoriaWhenFindInDatabaseByIdThenReturnCategoria() {

        testEntityManager.persistAndFlush(new Categoria(null,"Informática"));
        Categoria categoriaFound = categoriaRepository.findById(1).get();

        Assert.assertEquals("Informática",categoriaFound.getNome());
    }

    @Test
    public void givenValidCategoriaWhenFindByNameThenReturnCategoria() {
        testEntityManager.persistAndFlush(new Categoria(null, "Informática"));
        Categoria categoriaFound = categoriaRepository.findByNome("Informática");
        Assert.assertNotNull(categoriaFound);
    }

    @Test
    public void givenInvalidCategoriaWhenFindInDatabaseThenReturnEmpty(){
        Assert.assertFalse(categoriaRepository.findById(1).isPresent());
    }

    @Test
    public void givenValidCategoriaWhenDeleteInDatabaseThenCheckIfIsDeleted() {
        Categoria categoriaPersisted = testEntityManager.persistAndFlush(new Categoria(null, "Geek"));
        categoriaRepository.deleteById(categoriaPersisted.getId());
        Assert.assertFalse(categoriaRepository.findById(1).isPresent());
    }

    @Test
    public void givenValidCategoriaWhenUpdateNameThenNewNameMustBeDisplayedAfterSearchAgain(){
        Categoria categoriaUpdate = testEntityManager.persistAndFlush(new Categoria(null, "Nerd"));
        categoriaUpdate.setNome("Geek");
        categoriaRepository.save(categoriaUpdate);
        Categoria categoriaFound = categoriaRepository.findByNome("Geek");
        Assert.assertEquals(categoriaFound.getNome(), "Geek");
    }
}
