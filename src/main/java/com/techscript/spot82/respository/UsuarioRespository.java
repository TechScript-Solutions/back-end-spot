package com.techscript.spot82.respository;

import com.techscript.spot82.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRespository extends JpaRepository<User, UUID> {

    Optional<User> roles(String role);

    Optional<User> nickname(String nickname);

    boolean existsByNickname(String nickname);

    boolean existsByContact(String contact);

    void deleteByNickname(String nickname);

}
