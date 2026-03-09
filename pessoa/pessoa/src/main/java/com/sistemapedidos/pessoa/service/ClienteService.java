package com.sistemapedidos.pessoa.service;

import com.sistemapedidos.pessoa.exception.NaoEncontradoException;
import com.sistemapedidos.pessoa.interfaces.ClienteServiceInterface;
import com.sistemapedidos.pessoa.model.Cliente;
import com.sistemapedidos.pessoa.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService implements ClienteServiceInterface {

	private final ClienteRepository clienteRepository;

	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Transactional
	@Override
	public Cliente cadastrar(String nome, String email, String cpf) {
		return clienteRepository.save(new Cliente(nome, email, cpf));
	}

	@Transactional(readOnly = true)
	@Override
	public Cliente buscarPorId(Long id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new NaoEncontradoException("Cliente não encontrado: " + id));
	}
}
