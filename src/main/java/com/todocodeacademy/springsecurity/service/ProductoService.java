package com.todocodeacademy.springsecurity.service;

import com.todocodeacademy.springsecurity.model.Producto;
import com.todocodeacademy.springsecurity.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService {
    @Autowired
    IProductoRepository productoRepository;

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }
    @Override
    public List<Producto> findAllByEstado(String estado) {
        return productoRepository.findAllByEstado(estado);
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public void delete(Producto producto) {
        productoRepository.delete(producto);
    }

    @Override
    public Producto update(Producto producto) {
        return productoRepository.save(producto);
    }
}
