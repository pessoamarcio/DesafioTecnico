package com.sistemapedidos.pessoa.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoCreateRequest(
        @NotNull(message = "clienteId é obrigatório")
        Long clienteId,
        @NotEmpty(message = "itens é obrigatório")
        List<@NotNull PedidoItemRequest> itens
) {
}
