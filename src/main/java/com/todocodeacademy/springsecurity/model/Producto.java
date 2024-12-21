package com.todocodeacademy.springsecurity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private double precio;
    private String imageUrl;
    private String estado ="Activo";

    public Producto(String nombre, double precio, String imageUrl) {
        this.nombre = nombre;
        this.precio = precio;
        this.imageUrl = imageUrl;
    }
}
