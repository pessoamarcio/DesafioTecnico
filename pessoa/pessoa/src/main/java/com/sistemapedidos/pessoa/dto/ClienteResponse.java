package com.sistemapedidos.pessoa.dto;

import com.sistemapedidos.pessoa.model.Cliente;
import com.sistemapedidos.pessoa.model.StatusCliente;

import java.util.UUID;

public record ClienteResponse(UUID id, String nome, String email, String cpf, StatusCliente status) {
	public static ClienteResponse from(Cliente cliente) {
		return new ClienteResponse(
				cliente.getId(),
				cliente.getNome(),
				cliente.getEmail(),
				cliente.getCpf(),
				cliente.getStatus()
		);
	}
}
