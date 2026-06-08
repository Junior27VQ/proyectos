package com.krakedev.proyectos.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.services.TareaService;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final TareaService service;

    public TareaController(TareaService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> crear(@RequestBody Tarea t) {
        try {
            // Nota: En tu TareaService.crear, recuerda llamar a tareaRepo.save(nuevo) al final
            return new ResponseEntity<>(service.crear(t), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> listar() {
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }
 // ... dentro de TareaController.java ...

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable int id) {
        Tarea t = service.buscar(id);
        return (t != null) ? new ResponseEntity<>(t, HttpStatus.OK) 
                           : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Tarea t) {
        Tarea actualizado = service.actualizar(id, t);
        return (actualizado != null) ? new ResponseEntity<>(actualizado, HttpStatus.OK) 
                                     : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        return service.eliminar(id) ? new ResponseEntity<>(HttpStatus.OK) 
                                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}