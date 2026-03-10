package com.sistemapedidos.pessoa.service;

import com.sistemapedidos.pessoa.utils.StreamUtils;
import com.sistemapedidos.pessoa.model.Cliente;
import com.sistemapedidos.pessoa.repository.ClienteRepository;
import com.sistemapedidos.pessoa.model.ItemPedido;
import com.sistemapedidos.pessoa.model.Pedido;
import com.sistemapedidos.pessoa.repository.PedidoRepository;
import com.sistemapedidos.pessoa.model.StatusPedido;
import com.sistemapedidos.pessoa.model.Produto;
import com.sistemapedidos.pessoa.repository.ProdutoRepository;
import com.sistemapedidos.pessoa.model.StatusProduto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PedidoService{

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository
    )
        {
            this.pedidoRepository = pedidoRepository;
            this.clienteRepository = clienteRepository;
            this.produtoRepository = produtoRepository;
        }

    public Pedido criarPedido(Long clientId, Map<Long, Integer> itens){
        Map<Long, Integer> quantidadePorProduto = validarQuantidade(itens);

        Cliente cliente = clienteRepository.findById(pedidoId);
        .orElseThrow(() -> new NãoEncontratoException("Cliente não encontrato" + clientId));

        List<Produto> produtos = produtoRepository.findAllByIdForUpdate(quantidadePorProduto.keySet());
        validarProdutosEncontrados(quantidadePorProduto.keySet(), produtos);

        for (Produto produto : produtos){
            int quantidade = quantidadePorProduto.get(produto.getId());
            if(produto.getStatus() != StatusProduto.DISPONIVEL){
                throw new RegraNegocioException("Produto INDISPONIVEL: " + produto.getId());
            }
            if (!produto.podeVender(quantidade)) {
                throw new RegraNegocioException("Produto sem estoque: " + produto.getId());
            }
        }
        for (Produto produto : produtos) {
            produto.baixarEstoque(quantidadePorProduto.get(produto.getId()));
        }

        Map<Long, Produto> produtoPorId = produtos.stream()
                .collect(Collectors.toMap(Produto::getId, Function.identity()));

        Pedido pedido = new Pedido(cliente);
        for (var entry : quantidadePorProduto.entrySet()) {
            Produto produto = produtoPorId.get(entry.getKey());
            pedido.adicionarItem(new ItemPedido(produto, entry.getValue(), produto.getPreco()));
        }

        return pedidoRepository.save(pedido);
    }


    private static Map<Long, Integer> validarQuantidade(Map<Long, Integer> itens){
        if (itens == null || itens.isEmpty()){
            throw new RegraNegocioException(message: "Pedido deve conter ao menos 1 produto");
        }
        for (var entry : itens.entrySet()) {
            if (entry.getValue() == null){
                throw new RegraNegocioException(message: "Produto é obrigatórioi.");
            }
            Integer quantidade = entry.getValue()
            if(quantidade == null || quantidade <= 0){
                throw new RegraNegocioExcetion(message: "Quantidade deve ser maior que zero.")
            }
        }
        return itens;
    }

    private static void validarProdutosEncontrados(Set<Long> idsEsperados, List<Produto> produtosEncontrados) {
        Set<Long> encontrados = produtosEncontrados.stream().map(Produto::getId).collect(Collectors.toSet());
        for (Long id : idsEsperados) {
            if (!encontrados.contains(id)) {
                throw new NaoEncontradoException("Produto não encontrado: " + id);
            }
        }
    }

}