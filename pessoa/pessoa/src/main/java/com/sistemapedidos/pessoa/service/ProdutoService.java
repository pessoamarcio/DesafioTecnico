package com.sistemapedidos.pessoa.service;

import com.sistemapedidos.pessoa.exception.NaoEncontradoException;
import com.sistemapedidos.pessoa.model.Produto;
import com.sistemapedidos.pessoa.repository.ProdutoRepository;
import com.sistemapedidos.pessoa.model.StatusProduto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ProdutoService {

	private final ProdutoRepository produtoRepository;

	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}

	@Transactional
	public Produto cadastrar(String nome, BigDecimal preco, int quantidadeEmEstoque, StatusProduto status) {
		return produtoRepository.save(new Produto(nome, preco, quantidadeEmEstoque, status));
	}

	@Transactional(readOnly = true)
	public Produto buscarPorId(Long id) {
		return produtoRepository.findById(id)
				.orElseThrow(() -> new NaoEncontradoException("Produto não encontrado: " + id));
	}
}

