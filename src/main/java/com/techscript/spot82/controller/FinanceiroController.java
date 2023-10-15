package com.techscript.spot82.controller;

import com.techscript.spot82.services.FinanceiroServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/financeiro")
public class FinanceiroController {

    private FinanceiroServices financeiroServices;

    @GetMapping("/dia")
    public Double today(@RequestParam(name = "data") String data) {
        return financeiroServices.somaPagamentoDoDia(data);
    }

    @GetMapping("/personalizado")
    public Double sumAllDate(@RequestParam(name = "start") String startDate, @RequestParam(name = "end") String endDate) {
        return financeiroServices.somaPorDatas(startDate, endDate);
    }

    @GetMapping("/todo-periodo")
    public Double sumAll() {
        return financeiroServices.sumAll();
    }
}
