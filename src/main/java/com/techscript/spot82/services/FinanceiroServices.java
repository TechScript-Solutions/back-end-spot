package com.techscript.spot82.services;

import com.techscript.spot82.respository.PagamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class FinanceiroServices {

    private PagamentoRepository pagamentoRepository;

    public Double somaPagamentoDoDia(LocalDateTime data) {
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = formater.format(data);
        return pagamentoRepository.sumDayPagamento(dataFormatada);
    }

    public Double somaPorDatas(String dataInicial, String dataFinal) {
        return pagamentoRepository.sumAllDate(dataInicial, dataFinal);
    }

    public Double sumAll() {
        return pagamentoRepository.sumAll();
    }

}
