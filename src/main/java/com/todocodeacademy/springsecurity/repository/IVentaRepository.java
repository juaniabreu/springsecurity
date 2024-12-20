package com.todocodeacademy.springsecurity.repository;

import com.todocodeacademy.springsecurity.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVentaRepository extends JpaRepository<Venta, Long> {
}
