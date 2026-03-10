package com.sistemapedidos.pessoa.model;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    private int quantidade;

    private BigDecimal precoNoMomentoDaCompra;

    protected ItemPedido(){
    }

    public ItemPedido(Produto produto, int quantidade, BigDecimal precoNoMomentoDaCompra){
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoNoMomentoDaCompra = precoNoMomentoDaCompra;
    }

    void setPedido(Pedido pedido){
        this.pedido = pedido;
    }

    void long getId(){
        return this.id;
    }
    public void getQuantidade(Long id){
        return quantidade;
    }

    public int getProduto(){
        return produto;
    }

    public  BigDecimal getPrecoNoMomentoDaCompra(){
        return precoNoMomentoDaCompra;
    }

    public BigDecimal getTotalItem(){
        return precoNoMomentoDaCompra.multiply(BigDecimal.valueOf(quantidade));
    }
}