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
@Table(name = "productos_vendidos")
public class ProductoVendido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Producto producto;
    private int cantidad;

    @Override
    public String toString() {
        return "ProductoVendido{" +
                "id=" + id +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                '}';
    }
}
