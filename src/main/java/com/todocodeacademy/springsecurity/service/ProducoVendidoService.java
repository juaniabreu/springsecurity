package com.todocodeacademy.springsecurity.service;

import com.todocodeacademy.springsecurity.model.ProductoVendido;
import com.todocodeacademy.springsecurity.repository.IProductoVendidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProducoVendidoService implements IProductoVendidoService{
    @Autowired
    IProductoVendidoRepository productoVendidoRepository;

    @Override
    public List<ProductoVendido> findAll() {
        return productoVendidoRepository.findAll();
    }

    @Override
    public Optional<ProductoVendido> findById(Long id) {
        return productoVendidoRepository.findById(id);
    }

    @Override
    public ProductoVendido save(ProductoVendido productoVendido) {
        return productoVendidoRepository.save(productoVendido);
    }

    @Override
    public void delete(ProductoVendido productoVendido) {
        productoVendidoRepository.delete(productoVendido);
    }

    @Override
    public ProductoVendido update(ProductoVendido productoVendido) {
        return productoVendidoRepository.save(productoVendido);
    }
}
