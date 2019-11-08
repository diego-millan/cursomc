package com.diegomillan.cursomc.resources;

import com.diegomillan.cursomc.domain.Categoria;
import com.diegomillan.cursomc.dto.CategoriaDTO;
import com.diegomillan.cursomc.services.CategoriaService;
import com.diegomillan.cursomc.services.ICategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.tools.rmi.ObjectNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoriaResource.class)
public class CategoriaResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private ICategoriaService categoriaService;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void validateGetSuccess() {
        try {

            Mockito.when(categoriaService.find(1)).thenReturn(new Categoria());
            this.mockMvc.perform(get("/categorias").param("id", "1")).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json;charset=UTF-8"));
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void validateInsert() {
        Categoria categoria = new Categoria(1,"Informática");
        Mockito.when(categoriaService.insert(Mockito.any())).thenReturn(categoria);
        try {
            this.mockMvc.perform(post("/categorias")
                    .content(asJsonString(new CategoriaDTO(categoria)))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());

            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void validateUpdate() throws Exception {
        Categoria categoria = new Categoria(1,"Informática");
        Mockito.when(categoriaService.update(Mockito.any())).thenReturn(categoria);
        Mockito.when(categoriaService.fromDTO(Mockito.any())).thenReturn(categoria);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/categorias/{id}", 1)
                .content(asJsonString(new CategoriaDTO(categoria)))
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(builder)
                .andExpect(status().isNoContent());
        Assert.assertTrue(true);
    }

    @Test
    public void validateDelete() throws Exception {
        Categoria categoria = new Categoria(1,"Informática");
        Mockito.when(categoriaService.find(1)).thenReturn(categoria);
        mockMvc.perform(delete("/categorias/{id}",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
