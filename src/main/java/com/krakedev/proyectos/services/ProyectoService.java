package com.krakedev.proyectos.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.repositories.ProyectoRepository;

@Service
public class ProyectoService {

	private final ProyectoRepository proyectoRepo;

	public ProyectoService(ProyectoRepository repo) {
		this.proyectoRepo = repo;
	}

	public Proyecto guardar(Proyecto proyecto) {

		return proyectoRepo.save(proyecto);
	}

	public List<Proyecto> listar() {
		return proyectoRepo.findAll();
	}

	public Proyecto buscar(int id) {
		Optional<Proyecto> existe = proyectoRepo.findById(id);
		return existe.orElse(null);
	}

	public Proyecto actualizar(int id, Proyecto nuevo) {
		Proyecto proyecto = buscar(id);
		if (proyecto == null) {
			return null;
		}

		proyecto.setNombre(nuevo.getNombre());
		proyecto.setDescripcion(nuevo.getDescripcion());
		proyecto.setFechaInicio(nuevo.getFechaInicio());

		return proyectoRepo.save(proyecto);
	}

	public boolean eliminar(int id) {
		Proyecto existe = buscar(id);
		if (existe == null) {
			return false;
		}
		proyectoRepo.deleteById(id);
		return true;
	}
}