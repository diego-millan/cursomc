package com.diegomillan.cursomc.dto;

import com.diegomillan.cursomc.domain.Cliente;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty(message = "preenchimento obrigatório")
    @Length(min = 3, max = 80, message =  "O tamanho deve ser entre 3 e 80 caracteres")
    private String nome;

    @NotEmpty(message = "preenchimento obrigatório")
    @Email(message = "e-mail inválido")
    private String email;

    public ClienteDTO() {

    }

    public ClienteDTO(Cliente cliente) {
        id = cliente.getId();
        nome = cliente.getNome();
        email = cliente.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
