package com.sistemapedidos.pessoa.controller.pedido;

import com.sistemapedidos.pessoa.dto.PedidoCreateRequest;
import com.sistemapedidos.pessoa.dto.PedidoItemRequest;
import com.sistemapedidos.pessoa.dto.PedidoResponse;
import com.sistemapedidos.pessoa.model.Pedido;
import com.sistemapedidos.pessoa.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@RequestBody @Valid PedidoCreateRequest request) {
        Pedido pedido = pedidoService.criarPedido(
                request.clienteId(),
                toQuantidades(request.itens())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(PedidoResponse.from(pedido));
    }

    private static Map<UUID, Integer> toQuantidades(List<PedidoItemRequest> itens) {
        Map<UUID, Integer> quantidades = new HashMap<>();
        for (PedidoItemRequest item : itens) {
            quantidades.merge(item.produtoId(), item.quantidade(), Integer::sum);
        }
        return quantidades;
    }
}
