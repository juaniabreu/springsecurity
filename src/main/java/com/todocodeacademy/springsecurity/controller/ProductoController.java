package com.todocodeacademy.springsecurity.controller;

import com.todocodeacademy.springsecurity.model.Producto;
import com.todocodeacademy.springsecurity.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
 /*   @PutMapping("/update/{productoid}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Producto> update(@PathVariable Long productoid, @RequestBody Producto producto){
        Producto p = productoService.findById(productoid).get();
        p.setNombre(producto.getNombre());
        p.setPrecio(producto.getPrecio());
        productoService.save(p);
        return ResponseEntity.ok(p);
    }
   */
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Producto> create(@RequestParam("nombre") String nombre, @RequestParam("precio") Double precio,@RequestParam("imagen") MultipartFile imagen){
        try {
            Producto producto = productoService.save(nombre,precio,imagen);
            return ResponseEntity.ok(producto);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> actualizarProducto(
            @PathVariable Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") double precio,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        try {
            // Llamar al servicio para actualizar el producto
            Producto productoActualizado = productoService.update(id, nombre, precio, imagen);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al actualizar el producto: " + e.getMessage());
        }
    }

    @PutMapping("/active/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> activarProducto(@PathVariable Long id){
        Producto producto  = productoService.findById(id).get();
        producto.setEstado("Activo");
        productoService.save(producto);
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deactivarProducto(@PathVariable Long id){
        Producto producto  = productoService.findById(id).get();
        producto.setEstado("Inactivo");
        productoService.save(producto);
        return ResponseEntity.ok(producto);
    }


}
