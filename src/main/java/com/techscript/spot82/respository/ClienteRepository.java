package com.techscript.spot82.respository;

import com.techscript.spot82.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByVagaId(Long id);
    boolean existsByPlaca(String placa);

}
