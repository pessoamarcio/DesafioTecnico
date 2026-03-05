package com.sistemapedidos.pessoa.dto;

import com.sistemapedidos.pessoa.model.Produto;
import com.sistemapedidos.pessoa.model.StatusProduto;

import java.math.BigDecimal;

public record ProdutoResponse(
		Long id,
		String nome,
		BigDecimal preco,
		int quantidadeEmEstoque,
		StatusProduto status
) {
	public static ProdutoResponse from(Produto produto) {
		return new ProdutoResponse(
				produto.getId(),
				produto.getNome(),
				produto.getPreco(),
				produto.getQuantidadeEmEstoque(),
				produto.getStatus()
		);
	}
}

