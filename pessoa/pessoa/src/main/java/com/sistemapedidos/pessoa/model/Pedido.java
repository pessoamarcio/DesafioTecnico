package com.sistemapedidos.pessoa.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.OffsetDateTime;
import java.util.List;
import java.util.ArrayList;


@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy.GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status = statusPedido.CRIADO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ItemPedido> itens = new ArrayList<>();

    @Column(nullable = false)
    private OffsetDateTime criadoEm = OffsetDateTime.now();

    protected Pedido() {}

    public Pedido(Cliente cliente)
        {
            this.cliente = cliente;
        }

    public long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Status getStatus() {
        return status;
    }

    public OffsetDateTime getCriadoEm() {
        return criadoEm;
    }

    public List<ItemPedido> getItens(){
        return List.copyOf(itens);
    }

    public BigDecimal getValorTotal() {
        return itens.stream()
                .map(ItemPedido::getTotalItem)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean estaPago(){
        return status == StatusPedido.PAGO;
    }

    public boolean estaCancelado(){
        return status == StatusPedido.CANCELADO;
    }

    public void substituirItens(List<ItemPedido> novosItens){
        validarAlteracao();
        itens.clear();
        novosItens.forEach(this::adicionarItem);
    }

    private void validarAlteracao() {
        switch (status) {
            case PAGO -> throw new IllegalStateException("Pedido pago não pode ser alterado.");
            case CANCELADO -> throw new IllegalStateException("Pedido cancelado não pode ser alterado.");
            default -> {
            }
        }
    }

    public void adicionarItem(ItemPedido item){
        item.setPedido(this);
        itens.add(item);
    }

    public void pagar(){
        if(status == StatusPedido.CANCELADO){
            throw new IllegalStateException(s: "Pedido cancelado não pode ser pago.")
        }
    }

    public void cancelar(){
        switch (status) {
            case PAGO -> throw new IllegalStateException("Pedido pago nao pode ser alterado.");
            case CANCELADO -> {
                return;
            }
            default -> status = StatusPedido.CANCELADO;
        }
    }


}
