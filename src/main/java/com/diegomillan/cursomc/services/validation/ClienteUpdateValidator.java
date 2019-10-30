package com.diegomillan.cursomc.services.validation;

import com.diegomillan.cursomc.domain.Cliente;
import com.diegomillan.cursomc.dto.ClienteDTO;
import com.diegomillan.cursomc.repositories.ClienteRepository;
import com.diegomillan.cursomc.resources.exception.FieldErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    ClienteRepository clienteRepository;

    public void Initialize(ClienteInsert ann) {

    }

    @Override
    public boolean isValid(ClienteDTO clienteDTO, ConstraintValidatorContext context) {

        Map<String, String> map = (Map<String,String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldErrorMessage> errorMessageList = new ArrayList<>();

        Cliente clienteComEmailExistente = clienteRepository.findByEmail(clienteDTO.getEmail());
        if(clienteComEmailExistente != null && !clienteComEmailExistente.getId().equals(uriId)) {
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
