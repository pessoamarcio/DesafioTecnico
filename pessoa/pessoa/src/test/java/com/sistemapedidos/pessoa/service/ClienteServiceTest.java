package com.sistemapedidos.pessoa.service;

import com.sistemapedidos.pessoa.exception.NaoEncontradoException;
import com.sistemapedidos.pessoa.exception.RegraNegocioException;
import com.sistemapedidos.pessoa.model.Cliente;
import com.sistemapedidos.pessoa.model.StatusCliente;
import com.sistemapedidos.pessoa.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void cadastrarDeveSalvarClienteQuandoCpfEEmailNaoExistem() {
        String nome = "Maria";
        String email = "maria@email.com";
        String cpf = "12345678901";
        Cliente salvo = new Cliente(nome, email, cpf, StatusCliente.ATIVO);
        when(clienteRepository.existsByCpf(cpf)).thenReturn(false);
        when(clienteRepository.existsByEmailIgnoreCase(email)).thenReturn(false);
        when(clienteRepository.save(org.mockito.ArgumentMatchers.any(Cliente.class))).thenReturn(salvo);

        Cliente resultado = clienteService.cadastrar(nome, email, cpf, StatusCliente.ATIVO);

        assertSame(salvo, resultado);
        ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);
        verify(clienteRepository).save(captor.capture());
        assertEquals(nome, captor.getValue().getNome());
        assertEquals(email, captor.getValue().getEmail());
        assertEquals(cpf, captor.getValue().getCpf());
        assertEquals(StatusCliente.ATIVO, captor.getValue().getStatus());
    }

    @Test
    void cadastrarDeveLancarExcecaoQuandoCpfJaExiste() {
        String cpf = "12345678901";
        when(clienteRepository.existsByCpf(cpf)).thenReturn(true);

        assertThrows(RegraNegocioException.class,
                () -> clienteService.cadastrar("Maria", "maria@email.com", cpf, StatusCliente.ATIVO));
    }

    @Test
    void cadastrarDeveLancarExcecaoQuandoEmailJaExiste() {
        String email = "maria@email.com";
        when(clienteRepository.existsByCpf("12345678901")).thenReturn(false);
        when(clienteRepository.existsByEmailIgnoreCase(email)).thenReturn(true);

        assertThrows(RegraNegocioException.class,
                () -> clienteService.cadastrar("Maria", email, "12345678901", StatusCliente.ATIVO));
    }

    @Test
    void buscarPorIdDeveRetornarClienteQuandoEncontrado() {
        UUID id = UUID.randomUUID();
        Cliente cliente = new Cliente("Maria", "maria@email.com", "12345678901", StatusCliente.ATIVO);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        Cliente resultado = clienteService.buscarPorId(id);

        assertSame(cliente, resultado);
    }

    @Test
    void buscarPorIdDeveLancarExcecaoQuandoNaoEncontrado() {
        UUID id = UUID.randomUUID();
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NaoEncontradoException.class, () -> clienteService.buscarPorId(id));
    }
}
