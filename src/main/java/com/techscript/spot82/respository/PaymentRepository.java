package com.techscript.spot82.respository;

import com.techscript.spot82.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT SUM(t.payment) FROM Payment t WHERE t.date = :date")
    Double sumDayPayment(String date);

    @Query("SELECT SUM(t.payment) FROM Payment t WHERE t.date BETWEEN ?1 AND ?2")
    Double sumAllDate(String dataInicio, String dataFim);

    @Query("SELECT SUM(t.payment) FROM Payment t")
    Double sumAll();

    List<Payment> findByDate(String dateToday);

    Payment findByClientId(Long id);

}
