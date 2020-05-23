package com.github.beloin.SpringBootExpert.service;

import com.github.beloin.SpringBootExpert.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Regras de Negócio de um Domínio. -> Validações, Relatórios,Objeto que acessa a base de dados
// Precisa do seu repository
@Service
public class ClientesService {

    /*
        * Injeção de dependência se baseia em ter um SingleTon -> Para não ter diversas conexões
        * com o BD por exemplo, é usado um @AutoWired para pedir ao Spring esse mesmo Objeto em seu
        * Container
     */

    @Autowired
    private ClientesRepository repository;

/*    @Autowired
    public ClientesService(ClientesRepository repository) {
        this.repository = repository;
    }
     Se usar o construtor nem precisa usar essa anotation para usar a injeção de dependência.
    */
}
