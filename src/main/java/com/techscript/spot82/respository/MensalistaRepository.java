package com.techscript.spot82.respository;

import com.techscript.spot82.entities.Mensalista;
import com.techscript.spot82.enums.StatusPagamentoMensalista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MensalistaRepository extends JpaRepository<Mensalista, Long> {
    List<Mensalista> statusPagamento(StatusPagamentoMensalista atrasados);
}
