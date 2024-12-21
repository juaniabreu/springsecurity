package com.todocodeacademy.springsecurity.service;

import com.todocodeacademy.springsecurity.model.Producto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IProductoService {
    List<Producto> findAll();
    List<Producto> findAllByEstado(String estado);
    Optional<Producto> findById(Long id);
    Producto save(String nombre, double precio, MultipartFile imagen) throws IOException;
    void delete(Producto producto);
    Producto update(Long id, String nombre, double precio, MultipartFile imagen) throws IOException;
}
