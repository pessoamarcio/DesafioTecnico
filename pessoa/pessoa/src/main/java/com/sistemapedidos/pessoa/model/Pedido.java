package com.sistemapedidos.pessoa.model;

import com.sistemapedidos.pessoa.model.Cliente;
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
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status = StatusPedido.CRIADO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ItemPedido> itens = new ArrayList<>();

    @Column(nullable = false)
    private OffsetDateTime criadoEm = OffsetDateTime.now();

    protected Pedido() {
    }

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public OffsetDateTime getCriadoEm() {
        return criadoEm;
    }

    public List<ItemPedido> getItens() {
        return List.copyOf(itens);
    }

    public BigDecimal getValorTotal() {
        return itens.stream()
                .map(ItemPedido::getTotalItem)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean estaPago() {
        return status == StatusPedido.PAGO;
    }

    public boolean estaCancelado() {
        return status == StatusPedido.CANCELADO;
    }

    public void substituirItens(List<ItemPedido> novosItens) {
        if (status == StatusPedido.PAGO) {
            throw new IllegalStateException("Pedido PAGO não pode ser alterado.");
        }
        if (status == StatusPedido.CANCELADO) {
            throw new IllegalStateException("Pedido CANCELADO não pode ser alterado.");
        }
        itens.clear();
        for (ItemPedido item : novosItens) {
            adicionarItem(item);
        }
    }

    public void adicionarItem(ItemPedido item) {
        item.setPedido(this);
        itens.add(item);
    }

    public void pagar() {
        if (status == StatusPedido.CANCELADO) {
            throw new IllegalStateException("Pedido CANCELADO não pode ser pago.");
        }
        status = StatusPedido.PAGO;
    }

    public void cancelar() {
        if (status == StatusPedido.PAGO) {
            throw new IllegalStateException("Pedido PAGO não pode ser alterado.");
        }
        if (status == StatusPedido.CANCELADO) {
            return;
        }
        status = StatusPedido.CANCELADO;
    }
}

