package com.sistemapedidos.pessoa.service;

import com.sistemapedidos.pessoa.exception.NaoEncontradoException;
import com.sistemapedidos.pessoa.exception.RegraNegocioException;
import com.sistemapedidos.pessoa.interfaces.ClienteServiceInterface;
import com.sistemapedidos.pessoa.model.Cliente;
import com.sistemapedidos.pessoa.model.StatusCliente;
import com.sistemapedidos.pessoa.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ClienteService implements ClienteServiceInterface {

	private final ClienteRepository clienteRepository;

	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Transactional
	@Override
	public Cliente cadastrar(String nome, String email, String cpf, StatusCliente status) {
		if (clienteRepository.existsByCpf(cpf)) {
			throw new RegraNegocioException("CPF já cadastrado.");
		}
		if (clienteRepository.existsByEmailIgnoreCase(email)) {
			throw new RegraNegocioException("E-mail já cadastrado.");
		}
		return clienteRepository.save(new Cliente(nome, email, cpf, status));
	}

	@Transactional(readOnly = true)
	@Override
	public Cliente buscarPorId(UUID id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new NaoEncontradoException("Cliente não encontrado: " + id));
	}
}
