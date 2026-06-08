package com.krakedev.proyectos.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.services.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final UsuarioService userService;
	//private final UsuarioRepository userRepo;

	public AuthController(UsuarioService userService) {
		super();
		this.userService = userService;
		//this.userRepo = userRepo;
	}
	
	@PostMapping("/registrar")
	public ResponseEntity<?> registrar(@RequestBody Usuario usuario){
		try {
			Usuario nuevoUsuario = userService.guardar(usuario);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al registrar usuario: "+e.getMessage());
		}
	};
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales){
		
		String username = credenciales.get("username");
		String password = credenciales.get("password");
		
		Usuario autenticado = userService.autenticar(username, password);
		
		if(autenticado != null) {
		
			
			return ResponseEntity.ok(Map.of("token", autenticado));
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("El usuario o contraseña incorrecta");
		}
	};
	
	

}
