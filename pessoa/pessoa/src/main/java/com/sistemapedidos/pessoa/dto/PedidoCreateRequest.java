package com.sistemapedidos.pessoa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record PedidoCreateRequest(
        @NotNull(message = "clienteId e obrigatorio")
        @Schema(description = "UUID do cliente", example = "")
        UUID clienteId,
        @NotEmpty(message = "itens e obrigatorio")
        @Schema(description = "Lista de itens do pedido")
        List<@NotNull PedidoItemRequest> itens
) {
}
