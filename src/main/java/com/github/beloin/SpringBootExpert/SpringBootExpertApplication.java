package com.github.beloin.SpringBootExpert;

import com.github.beloin.SpringBootExpert.domain.entity.Cliente;
import com.github.beloin.SpringBootExpert.domain.entity.Pedido;
import com.github.beloin.SpringBootExpert.domain.repository.Clientes;
import com.github.beloin.SpringBootExpert.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SpringBootExpertApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes,
                                  @Autowired Pedidos pedidos) {
        return args -> {
            System.out.println("Criando e salvando CLiente");
            Cliente cliente = new Cliente("Sena Extreme");
            clientes.save(cliente);
            clientes.save(new Cliente("Daff Daffinha"));

            Pedido pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.setDataPedido(LocalDate.now());
            pedido.setTotal(BigDecimal.valueOf(150.20));

            pedidos.save(pedido);

//            Cliente cli1 = clientes.findClienteFetchPedidos(cliente.getId());
//            System.out.println(cli1);
//            System.out.println("Pedidos:" + cli1.getPedidos());

            Set<Pedido> pedidoSet = pedidos.findByCliente(cliente);
            pedidoSet.forEach(System.out::println);

            List<Cliente> cli = clientes.findAll();
            cli.forEach(System.out::println);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootExpertApplication.class, args);
    }
}
