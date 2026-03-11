package com.sistemapedidos.pessoa.repository;

import com.sistemapedidos.pessoa.model.Pedido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    @EntityGraph(attributePaths = {"cliente", "itens", "itens.produto"})
    Optional<Pedido> findById(UUID id);
}
