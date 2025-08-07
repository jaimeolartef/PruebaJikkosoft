package com.example.pruebajikkosft.model.services;

import com.example.pruebajikkosft.model.entities.ItemPedido;
import com.example.pruebajikkosft.model.repositories.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemPedidoService implements GenericService<ItemPedido, Integer> {

    private final ItemPedidoRepository itemPedidoRepository;

    @Autowired
    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Override
    public List<ItemPedido> findAll() {
        return itemPedidoRepository.findAll();
    }

    @Override
    public Optional<ItemPedido> findById(Integer id) {
        return itemPedidoRepository.findById(id);
    }

    @Override
    public ItemPedido save(ItemPedido itemPedido) {
        return itemPedidoRepository.save(itemPedido);
    }

    @Override
    public void deleteById(Integer id) {
        itemPedidoRepository.deleteById(id);
    }
}