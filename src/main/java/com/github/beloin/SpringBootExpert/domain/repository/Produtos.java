package com.github.beloin.SpringBootExpert.domain.repository;

import com.github.beloin.SpringBootExpert.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
