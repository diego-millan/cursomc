package com.diegomillan.cursomc.services.validation;

import com.diegomillan.cursomc.domain.Cliente;
import com.diegomillan.cursomc.domain.enums.TipoCliente;
import com.diegomillan.cursomc.dto.ClienteInsertionDTO;
import com.diegomillan.cursomc.repositories.ClienteRepository;
import com.diegomillan.cursomc.resources.exception.FieldErrorMessage;
import com.diegomillan.cursomc.services.validation.utils.DocumentUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteInsertionDTO> {

    @Autowired
    ClienteRepository clienteRepository;

    public void Initialize(ClienteInsert ann) {

    }

    @Override
    public boolean isValid(ClienteInsertionDTO clienteDTO, ConstraintValidatorContext context) {

        List<FieldErrorMessage> errorMessageList = new ArrayList<>();

        if (clienteDTO.getTipo().equals(TipoCliente.PESSOAFISICA.getCod())) {

            if(!DocumentUtil.isValidCPF(clienteDTO.getCpfOuCnpj())) {
                errorMessageList.add(new FieldErrorMessage("cpfOuCnpj", "CPF Inválido"));
            }
        }

        if (clienteDTO.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod())) {

            if(!DocumentUtil.isValidCNPJ(clienteDTO.getCpfOuCnpj())) {
                errorMessageList.add(new FieldErrorMessage("cpfOuCnpj", "CNPJ Inválido"));
            }
        }

        Cliente clienteComEmailExistente = clienteRepository.findByEmail(clienteDTO.getEmail());
        if(clienteComEmailExistente != null) {
            errorMessageList.add(new FieldErrorMessage("email", "E-mail já cadastrado no sistema"));
        }

        for(FieldErrorMessage e : errorMessageList) {

            context.disableDefaultConstraintViolation();

            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return errorMessageList.isEmpty();
    }
}
