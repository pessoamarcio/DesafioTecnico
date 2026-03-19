package com.sistemapedidos.pessoa.interfaces;

import com.sistemapedidos.pessoa.exception.NaoEncontradoException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiExceptionHandlerNaoEncontradoTest {

    private final ApiExceptionHandler handler = new ApiExceptionHandler();

    @Test
    void handleNaoEncontradoDeveRetornarNotFound() {
        ResponseEntity<ApiExceptionHandler.ApiErrorResponse> response =
                handler.handleNaoEncontrado(new NaoEncontradoException("nao encontrado"));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("nao encontrado", response.getBody().mensagem());
    }
}
