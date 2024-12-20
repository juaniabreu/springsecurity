package com.todocodeacademy.springsecurity.repository;

import com.todocodeacademy.springsecurity.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findAllByEstado(String estado);
}
