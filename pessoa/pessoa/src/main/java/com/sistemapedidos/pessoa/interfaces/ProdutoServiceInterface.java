package com.sistemapedidos.pessoa.interfaces;

import com.sistemapedidos.pessoa.model.Produto;
import com.sistemapedidos.pessoa.model.StatusProduto;
import java.math.BigDecimal;
import java.util.UUID;

public interface ProdutoServiceInterface {
	Produto cadastrar(String nome, BigDecimal preco, int quantidadeEmEstoque, StatusProduto status);
	Produto buscarPorId(UUID id);
}
