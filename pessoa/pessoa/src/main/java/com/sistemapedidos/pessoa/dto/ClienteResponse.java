package com.sistemapedidos.pessoa.dto;

import com.sistemapedidos.pessoa.model.Cliente;

public record ClienteResponse(Long id, String nome, String email, String cpf) {
	public static ClienteResponse from(Cliente cliente) {
		return new ClienteResponse(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getCpf());
	}
}
