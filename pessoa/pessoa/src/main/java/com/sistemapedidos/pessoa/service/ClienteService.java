package com.sistemapedidos.pessoa.service;

import com.sistemapedidos.pessoa.exception.NaoEncontradoException;
import com.sistemapedidos.pessoa.model.Cliente;
import com.sistemapedidos.pessoa.repository.ClienteRepository;
import com.sistemapedidos.pessoa.model.StatusCliente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;

	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Transactional
	public Cliente cadastrar(String nome, String email, StatusCliente status) {
		return clienteRepository.save(new Cliente(nome, email, status));
	}

	@Transactional(readOnly = true)
	public Cliente buscarPorId(Long id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new NaoEncontradoException("Cliente não encontrado: " + id));
	}
}

