package com.github.beloin.SpringBootExpert.service;

import com.github.beloin.SpringBootExpert.domain.entity.Pedido;
import com.github.beloin.SpringBootExpert.domain.enums.StatusPedido;
import com.github.beloin.SpringBootExpert.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
