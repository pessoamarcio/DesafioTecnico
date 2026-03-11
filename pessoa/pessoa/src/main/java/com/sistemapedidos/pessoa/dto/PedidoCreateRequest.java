package com.sistemapedidos.pessoa.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record PedidoCreateRequest(
        @NotNull(message = "clienteId é obrigatório")
        UUID clienteId,
        @NotEmpty(message = "itens é obrigatório")
        List<@NotNull PedidoItemRequest> itens
) {
}
