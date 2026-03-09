package com.sistemapedidos.pessoa.interfaces;

import com.sistemapedidos.pessoa.model.Cliente;

public interface ClienteServiceInterface {
	Cliente cadastrar(String nome, String email, String cpf);
	Cliente buscarPorId(Long id);
}
