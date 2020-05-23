package com.github.beloin.SpringBootExpert;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@Development
public class ApplicationConfiguration {

    @Bean
    public CommandLineRunner executar() {
        return args -> System.out.println("RODANDO EM AMBIENTE DE DESENVOLVIMENTO!");
    }


}
