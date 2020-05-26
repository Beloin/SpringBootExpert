package com.github.beloin.SpringBootExpert.domain.repository;

import com.github.beloin.SpringBootExpert.domain.entity.Cliente;
import com.github.beloin.SpringBootExpert.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface Pedidos extends JpaRepository<Pedido, Integer> {
    Set<Pedido> findByCliente(Cliente cliente);
}
