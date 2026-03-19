package com.sistemapedidos.pessoa.dto;

import com.sistemapedidos.pessoa.TestReflectionUtils;
import com.sistemapedidos.pessoa.model.Cliente;
import com.sistemapedidos.pessoa.model.StatusCliente;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClienteResponseTest {

    @Test
    void fromDeveMapearCliente() {
        Cliente cliente = new Cliente("Maria", "maria@email.com", "12345678901", StatusCliente.ATIVO);
        UUID id = UUID.randomUUID();
        TestReflectionUtils.setField(cliente, "id", id);

        ClienteResponse response = ClienteResponse.from(cliente);

        assertEquals(id, response.id());
        assertEquals("Maria", response.nome());
        assertEquals(StatusCliente.ATIVO, response.status());
    }
}
