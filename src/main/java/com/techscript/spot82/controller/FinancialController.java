package com.techscript.spot82.controller;

import com.techscript.spot82.services.FinancialServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/financeiro")
@CrossOrigin(origins = {"http://localhost:5173/", "http://localhost:4200/"})
public class FinancialController {

    private FinancialServices financialServices;

    @GetMapping("/dia")
    public Double today() {
        return financialServices.somaPagamentoDoDia(LocalDateTime.now());
    }

    @GetMapping("/personalizado")
    public Double sumAllDate(@RequestParam(name = "start") String startDate, @RequestParam(name = "end") String endDate) {
        return financialServices.somaPorDatas(startDate, endDate);
    }

    @GetMapping("/todo-periodo")
    public Double sumAll() {
        return financialServices.sumAll();
    }
}
