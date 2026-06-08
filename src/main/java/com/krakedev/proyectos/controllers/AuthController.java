package com.krakedev.proyectos.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.services.UsuarioService;
import com.krakedev.proyectos.services.TokenBlackListService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final UsuarioService userService;
	private final TokenBlackListService blacklist;

	public AuthController(UsuarioService userService, TokenBlackListService blacklist) {
		super();
		this.userService = userService;
		this.blacklist = blacklist;
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
	
	@PostMapping("/logout")
	public ResponseEntity<?> lohout(@RequestHeader(value = "Authorization", required = false) String authHeader){
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			blacklist.invalidarToken(token);
			return ResponseEntity.ok(Map.of("Mensaje", "Secion cerrada Exitosamente. Token Invalidado."));
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token no proporcionado.");
		}
	};
	
	@GetMapping("/perfil")
	public ResponseEntity<?> verPerfil(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String usuario = auth.getName();
		String rol = auth.getAuthorities().iterator().next().getAuthority();
		
		return ResponseEntity.ok(Map.of(
				"Mensaje", "Bienvenido al sistema protegido por Sping Security",
				"Usuario", usuario,
				"Rol", rol,
				"Estatus", "Autenticado exitosamente"
				));
	}

}
