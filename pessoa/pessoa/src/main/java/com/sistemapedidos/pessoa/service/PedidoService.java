package com.sistemapedidos.pessoa.service;

import com.sistemapedidos.pessoa.exception.NaoEncontradoException;
import com.sistemapedidos.pessoa.exception.RegraNegocioException;
import com.sistemapedidos.pessoa.model.Cliente;
import com.sistemapedidos.pessoa.model.ItemPedido;
import com.sistemapedidos.pessoa.model.Pedido;
import com.sistemapedidos.pessoa.model.StatusCliente;
import com.sistemapedidos.pessoa.model.Produto;
import com.sistemapedidos.pessoa.model.StatusProduto;
import com.sistemapedidos.pessoa.repository.ClienteRepository;
import com.sistemapedidos.pessoa.repository.PedidoRepository;
import com.sistemapedidos.pessoa.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Pedido criarPedido(UUID clienteId, Map<UUID, Integer> itens) {
        Map<UUID, Integer> quantidadePorProduto = validarQuantidades(itens);

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NaoEncontradoException("Cliente não encontrado: " + clienteId));

        if (cliente.getStatus() == StatusCliente.INATIVO) {
            throw new RegraNegocioException("Não permitir criar pedido para cliente INATIVO.");
        }

        List<Produto> produtos = produtoRepository.findAllByIdForUpdate(quantidadePorProduto.keySet());
        validarProdutosEncontrados(quantidadePorProduto.keySet(), produtos);

        for (Produto produto : produtos) {
            int quantidade = quantidadePorProduto.get(produto.getId());
            if (produto.getStatus() != StatusProduto.DISPONIVEL) {
                throw new RegraNegocioException("Produto INDISPONIVEL: " + produto.getId());
            }
            if (!produto.podeVender(quantidade)) {
                throw new RegraNegocioException("Produto sem estoque: " + produto.getId());
            }
        }

        for (Produto produto : produtos) {
            produto.baixarEstoque(quantidadePorProduto.get(produto.getId()));
        }

        Map<UUID, Produto> produtoPorId = produtos.stream()
                .collect(Collectors.toMap(Produto::getId, Function.identity()));

        Pedido pedido = new Pedido(cliente);
        for (var entry : quantidadePorProduto.entrySet()) {
            Produto produto = produtoPorId.get(entry.getKey());
            pedido.adicionarItem(new ItemPedido(produto, entry.getValue(), produto.getPreco()));
        }

        return pedidoRepository.save(pedido);
    }

    private static Map<UUID, Integer> validarQuantidades(Map<UUID, Integer> itens) {
        if (itens == null || itens.isEmpty()) {
            throw new RegraNegocioException("Pedido deve conter ao menos 1 produto.");
        }
        for (var entry : itens.entrySet()) {
            if (entry.getKey() == null) {
                throw new RegraNegocioException("Produto Ã© obrigatÃ³rio.");
            }
            Integer quantidade = entry.getValue();
            if (quantidade == null || quantidade <= 0) {
                throw new RegraNegocioException("Quantidade deve ser maior que zero.");
            }
        }
        return itens;
    }

    private static void validarProdutosEncontrados(Set<UUID> idsEsperados, List<Produto> produtosEncontrados) {
        Set<UUID> encontrados = produtosEncontrados.stream().map(Produto::getId).collect(Collectors.toSet());
        for (UUID id : idsEsperados) {
            if (!encontrados.contains(id)) {
                throw new NaoEncontradoException("Produto não encontrado: " + id);
            }
        }
    }
}
