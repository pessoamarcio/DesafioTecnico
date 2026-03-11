package com.sistemapedidos.pessoa.repository;

import com.sistemapedidos.pessoa.model.Produto;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select p from Produto p where p.id in :ids")
	List<Produto> findAllByIdForUpdate(@Param("ids") Collection<UUID> ids);
}
