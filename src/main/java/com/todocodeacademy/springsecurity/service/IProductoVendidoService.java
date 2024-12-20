package com.todocodeacademy.springsecurity.service;

import com.todocodeacademy.springsecurity.model.ProductoVendido;

import java.util.List;
import java.util.Optional;

public interface IProductoVendidoService {
    List<ProductoVendido> findAll();
    Optional<ProductoVendido> findById(Long id);
    ProductoVendido save(ProductoVendido productoVendido);
    void delete(ProductoVendido productoVendido);
    ProductoVendido update(ProductoVendido productoVendido);
}
