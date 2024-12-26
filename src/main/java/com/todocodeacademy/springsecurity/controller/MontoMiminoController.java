package com.todocodeacademy.springsecurity.controller;

import com.resend.*;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import com.todocodeacademy.springsecurity.model.MontoMinimo;
import com.todocodeacademy.springsecurity.service.MontoMinimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/monto")
@CrossOrigin("*")
public class MontoMiminoController {
    @Autowired
    MontoMinimoService montoMinimoService;

    @GetMapping
    public ResponseEntity<Double> monto() {
        return new ResponseEntity<>(montoMinimoService.getMontoMinimo(), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Double> montoPut(@RequestBody Double monto) {
        montoMinimoService.setMontoMinimo(monto);
        return new ResponseEntity<>(montoMinimoService.getMontoMinimo(), HttpStatus.OK);
    }


}
