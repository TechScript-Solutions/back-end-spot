package com.techscript.spot82.services;

import com.techscript.spot82.respository.PagamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FinanceiroServices {

    private PagamentoRepository pagamentoRepository;

    public Double somaPagamentoDoDia(String data) {
        return pagamentoRepository.sumDayPagamento(data);
    }

    public Double somaPorDatas(String dataInicial, String dataFinal) {
        return pagamentoRepository.sumAllDate(dataInicial, dataFinal);
    }

    public Double sumAll() {
        return pagamentoRepository.sumAll();
    }

}
