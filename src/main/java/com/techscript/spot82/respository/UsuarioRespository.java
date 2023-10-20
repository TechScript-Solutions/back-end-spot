package com.techscript.spot82.respository;

import com.techscript.spot82.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRespository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> papel(String papel);

    Optional<Usuario> nickname(String nickname);

    boolean existsByNickname(String nickname);

    boolean existsByContato(String contato);

    void deleteByNickname(String nickname);

}
