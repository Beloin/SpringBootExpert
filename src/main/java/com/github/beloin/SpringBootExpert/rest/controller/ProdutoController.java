package com.github.beloin.SpringBootExpert.rest.controller;

import com.github.beloin.SpringBootExpert.domain.entity.Cliente;
import com.github.beloin.SpringBootExpert.domain.entity.Produto;
import com.github.beloin.SpringBootExpert.domain.repository.Clientes;
import com.github.beloin.SpringBootExpert.domain.repository.Produtos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final Produtos produtos;

    ProdutoController(Produtos produtos) {
        this.produtos = produtos;
    }

    @GetMapping("{id}")
    public Produto getProdutobyId(@PathVariable Integer id) {
        return produtos.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody @Valid Produto produto) {
        return produtos.save(produto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        produtos.findById(id).map((p) -> {
            produtos.delete(p);
            return p;
        }).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid Produto produto, @PathVariable Integer id) {
        produtos.findById(id).map((p) -> {
            produto.setId(p.getId());
            produtos.save(produto);
            return produto;
        }).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }


    /**
     * Example Matcher usa para poder ter um filtro
     */
    @GetMapping
    public List<Produto> find(Produto filtro) {
        /// Tem como usar um SORT também (Ver documentação)
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Produto> example = Example.of(filtro, matcher);
        return produtos.findAll(example);
    }

}
