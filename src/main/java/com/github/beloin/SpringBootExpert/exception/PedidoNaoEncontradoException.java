package com.github.beloin.SpringBootExpert.exception;

public class PedidoNaoEncontradoException extends RuntimeException {
    public PedidoNaoEncontradoException() {
        super("Pedido não Encontrado");
    }
}
