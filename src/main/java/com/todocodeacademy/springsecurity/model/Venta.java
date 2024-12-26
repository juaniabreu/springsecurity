package com.todocodeacademy.springsecurity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String razonSocial;
    private String cuit;
    private double total;
    private String telefono;
    private int cantidad;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "venta_producto",
            joinColumns = @JoinColumn(name = "venta_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<ProductoVendido> productos = new ArrayList<>();

    private String tipoEnvio;
    private String formaPago;
    private String fecha;
    private String domicilio;
    private String email;
    private String estado="Pendiente";


    public String generarResumenVenta(List<ProductoVendido> productos) {
        StringBuilder html = new StringBuilder();
        html.append("<h1>Gracias por tu compra</h<1>");
        html.append("<p>Este es el resumen de tu compra:</p>");
        html.append("<table border='1' style='border-collapse: collapse;'>");
        html.append("<tr><th>Producto</th><th>Precio</th><th>Cantidad</th></tr>");

        double total = 0;
        for (ProductoVendido producto : productos) {
            html.append("<tr>")
                    .append("<td>").append(producto.getProducto().getNombre()).append("</td>")
                    .append("<td>$").append(producto.getProducto().getPrecio()).append("</td>")
                    .append("<td>").append(producto.getCantidad()).append("</td>")
                    .append("</tr>");
            total += producto.getProducto().getPrecio()*producto.getCantidad();
        }

        html.append("</table>");
        html.append("<p>Total: $").append(String.format("%.2f", total)).append("</p>");
        return html.toString();
    }

}
