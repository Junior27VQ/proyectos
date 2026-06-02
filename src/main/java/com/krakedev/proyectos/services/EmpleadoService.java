package com.krakedev.proyectos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.repositories.EmpleadoRepository;

@Service
public class EmpleadoService {
	private final EmpleadoRepository empleadoRepo;

	public EmpleadoService(EmpleadoRepository empleadoRepo) {
		this.empleadoRepo = empleadoRepo;
	}

	public Empleado guardar(Empleado empleado) {

		return empleadoRepo.save(empleado);
	}

	public List<Empleado> listar() {
		return empleadoRepo.findAll();
	}

	public Empleado buscar(int id) {
		Optional<Empleado> existe = empleadoRepo.findById(id);
		return existe.orElse(null);
	}

	public Empleado actualizar(int id, Empleado nuevo) {
		Empleado empleado = buscar(id);
		if (empleado == null) {
			return null;
		}
		empleado.setNombre(nuevo.getNombre());
		empleado.setCargo(nuevo.getCargo());

		return empleadoRepo.save(empleado);
	}

	public boolean eliminar(int id) {
		Empleado existe = buscar(id);
		if (existe == null) {
			return false;
		}
		empleadoRepo.deleteById(id);
		return true;
	}
}