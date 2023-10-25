package com.techscript.spot82.respository;

import com.techscript.spot82.entities.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VagaRepository extends JpaRepository<Vaga, Long> {

    Vaga vagaDoCliente(Long vaga);
    Vaga findByClienteId(Long id);
}
