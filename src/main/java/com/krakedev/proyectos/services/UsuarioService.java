package com.krakedev.proyectos.services;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.repositories.UsuarioRepository;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;

    public UsuarioService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public Usuario guardar(Usuario usuario) {
    	String contrasena = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
    	usuario.setPassword(contrasena);
    	
        return usuarioRepo.save(usuario);
    }

    public Usuario autenticar(String username, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepo.findByUsername(username);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            
            if (BCrypt.checkpw(password, usuario.getPassword())) {
                return usuario;
            }
        }
        return null;
    }
}