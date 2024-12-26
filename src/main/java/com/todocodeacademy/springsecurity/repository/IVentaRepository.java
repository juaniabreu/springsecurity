package com.todocodeacademy.springsecurity.repository;

import com.todocodeacademy.springsecurity.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVentaRepository extends JpaRepository<Venta, Long> {
}
