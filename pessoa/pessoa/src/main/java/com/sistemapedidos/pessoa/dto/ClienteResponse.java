package com.sistemapedidos.pessoa.dto;

import com.sistemapedidos.pessoa.model.Cliente;
import com.sistemapedidos.pessoa.model.StatusCliente;

public record ClienteResponse(Long id, String nome, String email, StatusCliente status) {
	public static ClienteResponse from(Cliente cliente) {
		return new ClienteResponse(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getStatus());
	}
}

