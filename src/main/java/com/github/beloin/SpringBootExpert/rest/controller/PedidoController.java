package com.github.beloin.SpringBootExpert.rest.controller;

import com.github.beloin.SpringBootExpert.domain.entity.ItemPedido;
import com.github.beloin.SpringBootExpert.domain.entity.Pedido;
import com.github.beloin.SpringBootExpert.domain.enums.StatusPedido;
import com.github.beloin.SpringBootExpert.rest.dto.AtualizaçãoStatusPedidoDTO;
import com.github.beloin.SpringBootExpert.rest.dto.InformacaoItemPedidoDTO;
import com.github.beloin.SpringBootExpert.rest.dto.InformacoesPedidoDTO;
import com.github.beloin.SpringBootExpert.rest.dto.PedidoDTO;
import com.github.beloin.SpringBootExpert.service.PedidoService;
import org.hibernate.mapping.Collection;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO dto) {
        return pedidoService.salvar(dto).getId();
    }

    // Patch -> Atualiza apenas uma parte da entidade
    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer id,
                             @RequestBody AtualizaçãoStatusPedidoDTO statusPedidoDTO) {
        String status = statusPedidoDTO.getNovoStatus();
        pedidoService.atualizaStatus(id, StatusPedido.valueOf(status));
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id) {
        return pedidoService.obterPedidoCompleto(id)
                .map(this::converter)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));
    }

    private InformacoesPedidoDTO converter(Pedido pedido) {
        return InformacoesPedidoDTO.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItens()))
                .build();
    }


    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }
        return itens.stream().map(itemPedido ->
                InformacaoItemPedidoDTO
                        .builder()
                        .descricaoProduto(itemPedido.getProduto().getDescricao())
                        .precoUnitario(itemPedido.getProduto().getPreco())
                        .quantidade(itemPedido.getQuantidade())
                        .build()
        ).collect(Collectors.toList());
    }

}
