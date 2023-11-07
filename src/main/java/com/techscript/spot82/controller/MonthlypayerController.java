package com.techscript.spot82.controller;

import com.techscript.spot82.entities.MonthlyPayer;
import com.techscript.spot82.services.MonthlypayerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/mensalistas")
@CrossOrigin(origins = {"http://localhost:5173/", "http://localhost:4200/"})
public class MonthlypayerController {

    private final MonthlypayerService monthlypayerService;

    @PostMapping
    public ResponseEntity<MonthlyPayer> criar(@Valid @RequestBody MonthlyPayer monthlyPayer, BindingResult result) {

        if (result.hasErrors()) {

            Map<String, String> erros = new HashMap<>();
            result.getFieldErrors().forEach(error -> erros.put(error.getField(), error.getDefaultMessage()));


        }

        MonthlyPayer novoMonthlyPayer = monthlypayerService.salvar(monthlyPayer);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMonthlyPayer);
    }

    @GetMapping
    public ResponseEntity<List<MonthlyPayer>> listar() {
        List<MonthlyPayer> listaMonthlyPayers = monthlypayerService.listar();
        return ResponseEntity.ok().body(listaMonthlyPayers);
    }

    @GetMapping("/atrasados")
    public ResponseEntity<List<MonthlyPayer>> mensalistasAtrasados() {
        return ResponseEntity.ok().body(monthlypayerService.mensalistasAtrasados());
    }

    @PutMapping("/pagamento/{cpf}")
    public ResponseEntity<MonthlyPayer> pagamentoMensalista(@PathVariable String cpf, @RequestBody @Valid MonthlyPayer monthlyPayer, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> erros = new HashMap<>();
            result.getFieldErrors().forEach(error -> erros.put(error.getField(), error.getDefaultMessage()));
        }

        monthlypayerService.pagamentoMensalista(cpf, monthlyPayer);

        return ResponseEntity.ok().body(monthlyPayer);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> removerMensalista(@PathVariable String cpf) {
        monthlypayerService.removerMensalista(cpf);
        return ResponseEntity.noContent().build();
    }

}
