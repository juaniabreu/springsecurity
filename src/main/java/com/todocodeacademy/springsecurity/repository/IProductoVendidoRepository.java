package com.todocodeacademy.springsecurity.repository;

import com.todocodeacademy.springsecurity.model.ProductoVendido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoVendidoRepository extends JpaRepository<ProductoVendido, Long> {
}
