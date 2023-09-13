package com.microservicios.CRUD.controller;


import com.microservicios.CRUD.dto.ProuductoDTO;
import com.microservicios.CRUD.model.Producto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/producto")
public class ProudctoController {

    private List<Producto> productos = new ArrayList<>(Arrays.asList(

            new Producto(1, "Producto 1", 100),
            new Producto(2, "Producto 2", 200),
            new Producto(3, "Producto 3", 300),
            new Producto(4, "Producto 4", 400),
            new Producto(212, "Producto 5 para test de id", 500)
    ));

    @GetMapping
    public ResponseEntity<List<Producto>> getAll() {
        return ResponseEntity.ok(productos);
    }

    @GetMapping("{id}")
    public ResponseEntity<Producto> getOne(@PathVariable("id") int id) {
        Producto producto = findById(id);
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<Producto> create(@RequestBody ProuductoDTO dto) {
        int index = productos.isEmpty() ? 1 : getLastIndex() + 1;
        Producto producto = Producto.builder().id(index).nombre(dto.getNombre()).price(dto.getPrice()).build();
        productos.add(producto);
        return ResponseEntity.ok(producto);
    }

    @PutMapping("{id}")
    public ResponseEntity<Producto> update(@PathVariable("id") int id, @RequestBody ProuductoDTO dto) {
        Producto producto = findById(id);
        producto.setNombre(dto.getNombre());
        producto.setPrice(dto.getPrice());
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Producto> delete(@PathVariable("id") int id) {
        Producto producto = findById(id);
        productos.remove(producto);
        return ResponseEntity.ok(producto);
    }

    private int getLastIndex() {
        return productos.stream().max(Comparator.comparing(Producto::getId)).get().getId();
    }

    private Producto findById(int id) {
        return productos.stream().filter(p -> p.getId() == id).findAny().orElse(null);
    }
}
















