package com.krakedev.proyectos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.repositories.EmpleadoRepository;
import com.krakedev.proyectos.repositories.ProyectoRepository;
import com.krakedev.proyectos.repositories.TareaRepository;

@Service
public class TareaService {
	private final TareaRepository tareaRepo;
	private final ProyectoRepository proyectoRepo;
	private final EmpleadoRepository empleadoRepo;

	public TareaService(TareaRepository tareaRepo, ProyectoRepository proyectoRepo, EmpleadoRepository empleadoRepo) {
		super();
		this.tareaRepo = tareaRepo;
		this.proyectoRepo = proyectoRepo;
		this.empleadoRepo = empleadoRepo;
	}

	public Tarea crear(Tarea nuevo) {
		Proyecto proyecto = proyectoRepo.findById(nuevo.getProyecto().getId())
				.orElseThrow(() -> new RuntimeException("El proyecto no existe."));
		List<Empleado> empleados = new ArrayList<>();
		for (Empleado e : nuevo.getEmpleados()) {
			Empleado existe = empleadoRepo.findById(e.getId())
					.orElseThrow(() -> new RuntimeException("El mecanico no existe."));
			;
			empleados.add(existe);
		}

		nuevo.setProyecto(proyecto);
		nuevo.setEmpleados(empleados);

		return nuevo;
	}

	public List<Tarea> listar() {
		return tareaRepo.findAll();
	}

	public Tarea buscar(int id) {
		Optional<Tarea> tarea = tareaRepo.findById(id);
		return tarea.orElse(null);
	}

	public Tarea actualizar(int id, Tarea nuevo) {
		Tarea tarea = buscar(id);
		if (tarea == null) {
			return null;
		}

		tarea.setDescripcion(nuevo.getDescripcion());
		tarea.setCostoEstimado(nuevo.getCostoEstimado());
		tarea.setFechaLimite(nuevo.getFechaLimite());

		return tareaRepo.save(tarea);
	}

	public boolean eliminar(int id) {
		Tarea existe = buscar(id);
		if (existe == null) {
			return false;
		}
		tareaRepo.deleteById(id);
		return true;
	}

}
