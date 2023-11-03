package com.techscript.spot82.respository;

import com.techscript.spot82.entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Query("SELECT SUM(t.pagamento) FROM Pagamento t WHERE t.data = :data")
    Double sumDayPagamento(String data);

    @Query("SELECT SUM(t.pagamento) FROM Pagamento t WHERE t.data BETWEEN ?1 AND ?2")
    Double sumAllDate(String dataInicio, String dataFim);

    @Query("SELECT SUM(t.pagamento) FROM Pagamento t")
    Double sumAll();

    Pagamento findByClienteId(Long id);

}
