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
            clientes.save(new Cliente("Sena Extreme"));
            clientes.save(new Cliente("Daff Daffinha"));

            List<Cliente> cli = clientes.findAll();
            cli.forEach(System.out::println);
            cli.forEach(c -> {
                c.setNome(c.getNome() + " Atualizado");
                clientes.save(c);
            });

            cli = clientes.findAll();
            cli.forEach(System.out::println);

            System.out.println("Encontrados com 'Sena':");
            System.out.println(clientes.findByNomeLike("Sena"));

            clientes.findAll().forEach(clientes::delete);

            System.out.println("Depois de serem deletados:");
            cli = clientes.findAll();
            cli.forEach(System.out::println);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootExpertApplication.class, args);
    }
}
