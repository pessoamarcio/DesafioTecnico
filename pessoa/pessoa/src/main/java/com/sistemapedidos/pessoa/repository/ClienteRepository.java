package com.sistemapedidos.pessoa.repository;

import com.sistemapedidos.pessoa.model.Cliente;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ClienteRepository {

	private final ClienteRepositoryJpa clienteRepositoryJpa;

	public ClienteRepository(ClienteRepositoryJpa clienteRepositoryJpa) {
		this.clienteRepositoryJpa = clienteRepositoryJpa;
	}

	public Cliente save(Cliente cliente) {
		return clienteRepositoryJpa.save(cliente);
	}

	public Optional<Cliente> findById(UUID id) {
		return clienteRepositoryJpa.findById(id);
	}

	public boolean existsByCpf(String cpf) {
		return clienteRepositoryJpa.existsByCpf(cpf);
	}

	public boolean existsByEmailIgnoreCase(String email) {
		return clienteRepositoryJpa.existsByEmailIgnoreCase(email);
	}
}
