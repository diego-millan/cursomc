package com.diegomillan.cursomc.resources;

import com.diegomillan.cursomc.domain.Categoria;
import com.diegomillan.cursomc.services.CategoriaService;
import com.diegomillan.cursomc.services.ICategoriaService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoriaResource.class)
public class CategoriaResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoriaService categoriaService;

    @Test
    public void validateGetSuccess() {
        try {

            Mockito.when(categoriaService.find(1)).thenReturn(new Categoria());
            Assert.assertTrue(true);
        } catch (ObjectNotFoundException e) {
            Assert.fail();
        }
    }
}
