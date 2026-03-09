package com.sistemapedidos.pessoa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteCreateRequest(
		@NotBlank(message = "nome é obrigatório")
		String nome,
		@NotBlank(message = "email é obrigatório")
		@Email(message = "email inválido")
		String email,
		@NotBlank(message = "cpf é obrigatório")
		String cpf
){}
