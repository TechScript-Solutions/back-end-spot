package com.techscript.spot82.respository;

import com.techscript.spot82.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRespository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> email(String email);
    Optional<Usuario> papel(String papel);
}
