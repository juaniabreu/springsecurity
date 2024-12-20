package com.todocodeacademy.springsecurity.service;

import com.todocodeacademy.springsecurity.model.Venta;
import com.todocodeacademy.springsecurity.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentaService implements IVentaService {
    @Autowired
    IVentaRepository ventaRepository;


    @Override
    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    @Override
    public Optional<Venta> findById(Long id) {
        return ventaRepository.findById(id);
    }

    @Override
    public Venta save(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Override
    public void delete(Venta venta) {
        ventaRepository.delete(venta);
    }

    @Override
    public Venta update(Venta venta) {
        return ventaRepository.save(venta);
    }
}
