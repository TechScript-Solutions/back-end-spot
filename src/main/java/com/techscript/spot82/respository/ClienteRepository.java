package com.techscript.spot82.respository;

import com.techscript.spot82.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findById(Long id);
    boolean existsByPlaca(String placa);

}
