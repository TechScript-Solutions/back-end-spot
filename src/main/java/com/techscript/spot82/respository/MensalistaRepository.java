package com.techscript.spot82.respository;

import com.techscript.spot82.entities.MonthlyPayer;
import com.techscript.spot82.enums.StatusPaymentMonthlypayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensalistaRepository extends JpaRepository<MonthlyPayer, Long> {

    List<MonthlyPayer> statusPayment(StatusPaymentMonthlypayer lateMonthly);
    MonthlyPayer cpf(String cpf);
}
