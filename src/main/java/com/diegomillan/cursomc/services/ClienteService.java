package com.diegomillan.cursomc.services;

import java.util.List;
import java.util.Optional;

import com.diegomillan.cursomc.domain.Cliente;
import com.diegomillan.cursomc.dto.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.diegomillan.cursomc.repositories.ClienteRepository;

import javassist.tools.rmi.ObjectNotFoundException;


@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) throws ObjectNotFoundException  {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(ClienteNotFoundException(id)));
	}
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		return repo.save(cliente);
	}

	public Cliente update(Cliente cliente) throws ObjectNotFoundException  {
		Cliente clienteAtualizacao = find(cliente.getId());
		updateData(clienteAtualizacao, cliente);
		return repo.save(clienteAtualizacao);
	}

	private void updateData(Cliente clienteOriginal, Cliente cliente) {
		clienteOriginal.setNome(cliente.getNome());
		clienteOriginal.setEmail(cliente.getEmail());
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

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest) ;
	}

	private String dataIntegrityException() {
		return "Não é possível excluir uma cliente que possui pedidos.";
	}

	private String clienteNotFoundException(Integer id) {
		return "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName();
	}

	private String ClienteNotFoundException(Integer id) {
		return "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName();
	}

	public Cliente fromDTO(ClienteDTO clienteDTO) {

		Cliente cliente = new Cliente(clienteDTO.getId(), clienteDTO.getNome(),
				clienteDTO.getEmail(), null, null) ;

		return cliente;
	}
}
