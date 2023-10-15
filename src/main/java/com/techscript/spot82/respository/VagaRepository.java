package com.techscript.spot82.respository;

import com.techscript.spot82.entities.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VagaRepository extends JpaRepository<Vaga, Long> {

    Vaga vagaDoCliente(Long quantidade);
}
