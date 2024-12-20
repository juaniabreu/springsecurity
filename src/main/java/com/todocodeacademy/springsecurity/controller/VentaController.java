package com.todocodeacademy.springsecurity.controller;

import com.todocodeacademy.springsecurity.model.Producto;
import com.todocodeacademy.springsecurity.model.ProductoVendido;
import com.todocodeacademy.springsecurity.model.Venta;
import com.todocodeacademy.springsecurity.service.ProductoService;
import com.todocodeacademy.springsecurity.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin("*")
public class VentaController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private VentaService ventaService;

    @GetMapping("/getall")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Venta>> getAll(){
        List<Venta> ventas = ventaService.findAll();
        return ResponseEntity.ok(ventas);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Venta> getById(@PathVariable Long id){
        Optional<Venta> venta = ventaService.findById(id);
        if(venta.isPresent()){
            return ResponseEntity.ok(venta.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Venta> create(@RequestBody Venta venta){

        Venta v  = new Venta();
        v.setCuit(venta.getCuit());
        v.setRazonSocial(venta.getRazonSocial());


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
        Date fecha = new Date();
        String formattedDate = formatter.format(fecha);
        v.setFecha(formattedDate);
        v.setTipoEnvio(venta.getTipoEnvio());
        if(venta.getTipoEnvio().toLowerCase()=="local"){
            v.setDomicilio("");
        }else{
            v.setDomicilio(venta.getDomicilio());
        }
        v.setTelefono(venta.getTelefono());
        v.setFormaPago(venta.getFormaPago());
        v.setEstado(venta.getEstado());
        v.setTotal(venta.getTotal());

        List<ProductoVendido> vendidos = new ArrayList<>();
        for(ProductoVendido p : venta.getProductos()){

            Long productoId = p.getProducto().getId();
            Producto producto = productoService.findById(productoId).orElseThrow(()-> new NoSuchElementException("Producto no encontrado" + productoId));
            ProductoVendido pv = new ProductoVendido();
            pv.setProducto(producto);
            pv.setCantidad(p.getCantidad());
            vendidos.add(pv);
        }
        v.setProductos(vendidos);


        ventaService.save(v);

        return ResponseEntity.ok(v);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Venta> update(@PathVariable Long id, @RequestBody String estado){
        Venta v = ventaService.findById(id).get();
        v.setEstado(estado.replace("\"", ""));
        ventaService.save(v);
        return ResponseEntity.ok(v);
    }
}
