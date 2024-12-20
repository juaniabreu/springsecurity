package com.todocodeacademy.springsecurity.controller;

import com.todocodeacademy.springsecurity.model.Producto;
import com.todocodeacademy.springsecurity.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin("*")
public class ProductoController {
    @Autowired
    private ProductoService productoService;


    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Producto>> getAll(){
        List<Producto> productos = productoService.findAll();
        return ResponseEntity.ok(productos);
    }
    @GetMapping("/getactive")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<List<Producto>> getAllActive(){
        List<Producto> productos = productoService.findAllByEstado("Activo");
        return ResponseEntity.ok(productos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getById(@PathVariable Long id){
       Optional<Producto> producto = productoService.findById(id);
       if(producto.isPresent()){
           return ResponseEntity.ok(producto.get());
       }
       return ResponseEntity.notFound().build();
    }
    @PutMapping("/update/{productoid}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Producto> update(@PathVariable Long productoid, @RequestBody Producto producto){
        Producto p = productoService.findById(productoid).get();
        p.setNombre(producto.getNombre());
        p.setPrecio(producto.getPrecio());
        productoService.save(p);
        return ResponseEntity.ok(p);
    }
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Producto> create(@RequestBody Producto producto){
        productoService.save(producto);
        return ResponseEntity.ok(producto);
    }
}
