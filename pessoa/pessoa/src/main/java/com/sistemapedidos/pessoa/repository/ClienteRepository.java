package com.sistemapedidos.pessoa.repository;

import com.sistemapedidos.pessoa.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
}
