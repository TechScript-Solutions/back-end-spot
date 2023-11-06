package com.techscript.spot82.services;

import com.techscript.spot82.respository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class FinancialServices {

    private PaymentRepository paymentRepository;

    public Double somaPagamentoDoDia(LocalDateTime data) {
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = formater.format(data);
        return paymentRepository.sumDayPayment(dataFormatada);
    }

    public Double somaPorDatas(String dataInicial, String dataFinal) {
        return paymentRepository.sumAllDate(dataInicial, dataFinal);
    }

    public Double sumAll() {
        return paymentRepository.sumAll();
    }

}
