package com.sistemapedidos.pessoa.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record PedidoItemRequest(
        @NotNull(message = "produtoId é obrigatório")
        UUID produtoId,
        @Positive(message = "quantidade deve ser > 0")
        int quantidade
) {
}
