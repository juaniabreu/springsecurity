package com.todocodeacademy.springsecurity.repository;

import com.todocodeacademy.springsecurity.model.MontoMinimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMontoMinimoRepository extends JpaRepository<MontoMinimo, Long> {

}
