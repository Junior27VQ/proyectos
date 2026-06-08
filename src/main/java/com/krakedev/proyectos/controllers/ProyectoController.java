package com.krakedev.proyectos.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.services.ProyectoService;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private final ProyectoService service;

    public ProyectoController(ProyectoService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROL_ADMIN')")
    public ResponseEntity<?> crear(@RequestBody Proyecto p) {
        try {
            service.guardar(p); 
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROL_ADMIN','USER')")
    public ResponseEntity<?> listar() {
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable int id) {
        Proyecto p = service.buscar(id);
        return (p != null) ? new ResponseEntity<>(p, HttpStatus.OK) 
                           : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Proyecto p) {
        Proyecto actualizado = service.actualizar(id, p);
        return (actualizado != null) ? new ResponseEntity<>(actualizado, HttpStatus.OK) 
                                     : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROL_ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        return service.eliminar(id) ? new ResponseEntity<>(HttpStatus.OK) 
                                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}