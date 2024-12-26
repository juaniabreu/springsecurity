package com.todocodeacademy.springsecurity.service;

import com.todocodeacademy.springsecurity.model.MontoMinimo;
import com.todocodeacademy.springsecurity.repository.IMontoMinimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MontoMinimoService implements IMontoMinimoService {
    @Autowired
    IMontoMinimoRepository montoMinimoRepository;

    @Override
    public Double getMontoMinimo() {
        MontoMinimo montoMinimo = montoMinimoRepository.findById(1L).get();
        return montoMinimo.getMontoMinimo();

    }

    @Override
    public void setMontoMinimo(Double montoMinimo) {
        MontoMinimo montoMin = montoMinimoRepository.findById(1L).get();
        montoMin.setMontoMinimo(montoMinimo);
        montoMinimoRepository.save(montoMin);
    }
}
