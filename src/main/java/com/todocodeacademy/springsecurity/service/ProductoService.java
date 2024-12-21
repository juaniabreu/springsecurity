package com.todocodeacademy.springsecurity.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.todocodeacademy.springsecurity.model.Producto;
import com.todocodeacademy.springsecurity.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService {
    @Autowired
    IProductoRepository productoRepository;

    @Autowired
    private Cloudinary cloudinary;
    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }
    @Override
    public List<Producto> findAllByEstado(String estado) {
        return productoRepository.findAllByEstado(estado);
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto save(String nombre, double precio, MultipartFile imagen) throws IOException {
        Map<String,Object> uploadResult= cloudinary.uploader().upload(imagen.getBytes(), ObjectUtils.emptyMap());
        String imagenUrl = (String) uploadResult.get("url");
        Producto producto = new Producto(nombre,precio,imagenUrl);
        return productoRepository.save(producto);
    }

    @Override
    public void delete(Producto producto) {
        productoRepository.delete(producto);
    }
    @Override
    public Producto update(Long id, String nombre, double precio, MultipartFile imagen) throws IOException {
        // Buscar el producto existente
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Actualizar los datos básicos
        producto.setNombre(nombre);
        producto.setPrecio(precio);

        // Si se proporciona una nueva imagen, reemplazar la existente
        if (imagen != null && !imagen.isEmpty()) {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(imagen.getBytes(), ObjectUtils.emptyMap());
            String imagenUrl = (String) uploadResult.get("url");
            producto.setImageUrl(imagenUrl); // Actualizar URL de la imagen
        }

        // Guardar los cambios en la base de datos
        return productoRepository.save(producto);
    }

    public void save(Producto p) {
        productoRepository.save(p);
    }
}
