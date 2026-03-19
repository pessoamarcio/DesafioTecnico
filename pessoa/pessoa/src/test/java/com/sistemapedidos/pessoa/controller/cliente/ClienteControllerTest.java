package com.sistemapedidos.pessoa.controller.cliente;

import com.sistemapedidos.pessoa.TestReflectionUtils;
import com.sistemapedidos.pessoa.dto.ClienteCreateRequest;
import com.sistemapedidos.pessoa.dto.ClienteResponse;
import com.sistemapedidos.pessoa.interfaces.ClienteServiceInterface;
import com.sistemapedidos.pessoa.model.Cliente;
import com.sistemapedidos.pessoa.model.StatusCliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteServiceInterface clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @Test
    void cadastrarDeveRetornarCreatedComClienteResponse() {
        ClienteCreateRequest request = new ClienteCreateRequest("Maria", "maria@email.com", "12345678901", StatusCliente.ATIVO);
        Cliente cliente = new Cliente(request.nome(), request.email(), request.cpf(), request.status());
        UUID id = UUID.randomUUID();
        TestReflectionUtils.setField(cliente, "id", id);
        when(clienteService.cadastrar(request.nome(), request.email(), request.cpf(), request.status())).thenReturn(cliente);

        ResponseEntity<ClienteResponse> response = clienteController.cadastrar(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertInstanceOf(ClienteResponse.class, response.getBody());
        assertEquals(id, response.getBody().id());
    }

    @Test
    void buscarPorIdDeveRetornarClienteResponse() {
        UUID id = UUID.randomUUID();
        Cliente cliente = new Cliente("Maria", "maria@email.com", "12345678901", StatusCliente.ATIVO);
        TestReflectionUtils.setField(cliente, "id", id);
        when(clienteService.buscarPorId(id)).thenReturn(cliente);

        ClienteResponse response = clienteController.buscarPorId(id);

        assertEquals(id, response.id());
        assertEquals("Maria", response.nome());
    }
}
