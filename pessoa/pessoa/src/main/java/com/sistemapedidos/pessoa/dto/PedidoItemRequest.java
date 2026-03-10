package com.sistemapedidos.pessoa.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PedidoItemRequest(
        @NotNull(message = "produtoId é obrigatório")
        Long produtoId,
        @Positive(message = "quantidade deve ser > 0")
        int quantidade
) {
}

