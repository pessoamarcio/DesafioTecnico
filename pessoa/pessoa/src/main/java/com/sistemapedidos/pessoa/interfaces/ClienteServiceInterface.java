package com.sistemapedidos.pessoa.interfaces;

import com.sistemapedidos.pessoa.model.Cliente;
import com.sistemapedidos.pessoa.model.StatusCliente;

import java.util.UUID;

public interface ClienteServiceInterface {
	Cliente cadastrar(String nome, String email, String cpf, StatusCliente status);
	Cliente buscarPorId(UUID id);
}
