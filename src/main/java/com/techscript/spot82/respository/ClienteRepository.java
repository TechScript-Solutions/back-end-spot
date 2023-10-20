package com.techscript.spot82.respository;

import com.techscript.spot82.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByPlaca(String placa);
    boolean existsByPlaca(String placa);

}
