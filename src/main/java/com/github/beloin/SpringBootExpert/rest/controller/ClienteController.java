package com.github.beloin.SpringBootExpert.rest.controller;

import com.github.beloin.SpringBootExpert.domain.entity.Cliente;
import com.github.beloin.SpringBootExpert.domain.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }


    @GetMapping("{id}")
    public Cliente getClienteById(@PathVariable Integer id) {
        return clientes.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não Encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@Valid @RequestBody Cliente cliente) {
        return clientes.save(cliente);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        clientes.findById(id).map((cliente -> {
            clientes.delete(cliente);
            return cliente;
        })).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente Não Encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid Cliente cliente, @PathVariable Integer id) {
        clientes.findById(id).map(clienteExistente -> {
            cliente.setId(clienteExistente.getId());
            clientes.save(cliente);
            return clienteExistente;
        }).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente Não Encontrado"));
    }

    @GetMapping
    public List<Cliente> find(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Cliente> example = Example.of(filtro, matcher);
        return clientes.findAll(example);
    }

}
