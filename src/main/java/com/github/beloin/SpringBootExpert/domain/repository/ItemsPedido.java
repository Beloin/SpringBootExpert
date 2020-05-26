package com.github.beloin.SpringBootExpert.domain.repository;

import com.github.beloin.SpringBootExpert.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedido extends JpaRepository<ItemPedido, Integer> {
}
