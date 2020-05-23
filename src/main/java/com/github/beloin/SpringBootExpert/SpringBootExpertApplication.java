package com.github.beloin.SpringBootExpert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootExpertApplication {

    @Value("${application.name}")
    private String applicationName;

    @GetMapping("/getAppName")
    public String appName() {
        return applicationName;
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }

    @Cachorro
    private Animal animal;

    @Bean("executarAnimal")
    public CommandLineRunner executar() {
        return args -> {
            this.animal.fazerBarulho();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootExpertApplication.class, args);
    }

}
