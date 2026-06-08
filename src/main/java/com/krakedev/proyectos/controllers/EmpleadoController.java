package com.krakedev.proyectos.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.services.EmpleadoService;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService service;

    public EmpleadoController(EmpleadoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Empleado e) {
        try {
            service.guardar(e);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e_err) {
            return new ResponseEntity<>(e_err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROL_ADMIN','USER')")
    public ResponseEntity<?> listar() {
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable int id) {
        Empleado e = service.buscar(id);
        return (e != null) ? new ResponseEntity<>(e, HttpStatus.OK) 
                           : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Empleado e) {
        Empleado actualizado = service.actualizar(id, e);
        return (actualizado != null) ? new ResponseEntity<>(actualizado, HttpStatus.OK) 
                                     : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        return service.eliminar(id) ? new ResponseEntity<>(HttpStatus.OK) 
                                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}