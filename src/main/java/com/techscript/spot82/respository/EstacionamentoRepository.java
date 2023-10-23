package com.techscript.spot82.respository;

import com.techscript.spot82.entities.Estacionamento;
import com.techscript.spot82.enums.TaxaPorAtraso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EstacionamentoRepository extends JpaRepository<Estacionamento, Long> {

    Estacionamento findBytaxaPorAtraso(TaxaPorAtraso taxa);
    @Query("SELECT e.valor FROM Estacionamento e")
    Double valor();

}
