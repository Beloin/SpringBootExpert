package com.github.beloin.SpringBootExpert.service.impl;

import com.github.beloin.SpringBootExpert.domain.entity.Cliente;
import com.github.beloin.SpringBootExpert.domain.entity.ItemPedido;
import com.github.beloin.SpringBootExpert.domain.entity.Pedido;
import com.github.beloin.SpringBootExpert.domain.entity.Produto;
import com.github.beloin.SpringBootExpert.domain.enums.StatusPedido;
import com.github.beloin.SpringBootExpert.domain.repository.Clientes;
import com.github.beloin.SpringBootExpert.domain.repository.ItemsPedido;
import com.github.beloin.SpringBootExpert.domain.repository.Pedidos;
import com.github.beloin.SpringBootExpert.domain.repository.Produtos;
import com.github.beloin.SpringBootExpert.exception.PedidoNaoEncontradoException;
import com.github.beloin.SpringBootExpert.exception.RegraNegocioException;
import com.github.beloin.SpringBootExpert.rest.dto.ItemPedidoDTO;
import com.github.beloin.SpringBootExpert.rest.dto.PedidoDTO;
import com.github.beloin.SpringBootExpert.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedido itemsPedidoRepository;


    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(dto.getCliente())
                .orElseThrow(() -> new RegraNegocioException("Cliente inválido."));
        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemPedidos = salvarItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemPedidos);
        pedido.setItens(itemPedidos);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow(PedidoNaoEncontradoException::new);
    }

    private List<ItemPedido> salvarItems(Pedido pedido, List<ItemPedidoDTO> items) {
        if (items.isEmpty())
            throw new RegraNegocioException("Não é possível realizar um pedido sem items");
        return items
                .stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(() ->
                                    new RegraNegocioException("Código de Produto inválido" + idProduto));
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }

    // Ctrl + shift + F9 recompila
    
}
