package com.todocodeacademy.springsecurity.service;

import com.todocodeacademy.springsecurity.model.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    List<Producto> findAll();
    List<Producto> findAllByEstado(String estado);
    Optional<Producto> findById(Long id);
    Producto save(Producto producto);
    void delete(Producto producto);
    Producto update(Producto producto);
}
