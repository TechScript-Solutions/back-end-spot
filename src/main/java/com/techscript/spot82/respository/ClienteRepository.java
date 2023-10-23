package com.techscript.spot82.respository;

import com.techscript.spot82.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByVagaId(Long id);
    boolean existsByPlaca(String placa);

}
