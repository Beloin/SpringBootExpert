package com.github.beloin.SpringBootExpert;

import com.github.beloin.SpringBootExpert.domain.entity.Cliente;
import com.github.beloin.SpringBootExpert.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
public class SpringBootExpertApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes) {
        return args -> {
            clientes.salvarCliente(new Cliente("Sena Extreme"));
            clientes.salvarCliente(new Cliente("Daff Daffinha"));

            List<Cliente> cli = clientes.obterTodos();
            cli.forEach(System.out::println);

            cli.forEach(c -> {
                c.setNome(c.getNome() + " Atualizado");
                clientes.atualizar(c);
            });

            cli = clientes.obterTodos();
            cli.forEach(System.out::println);

            clientes.deletar(clientes.buscarPorNome("Se").get(0));

            cli = clientes.obterTodos();
            cli.forEach(System.out::println);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootExpertApplication.class, args);
    }
}
