package com.techscript.spot82.controller;

import com.techscript.spot82.entities.Parking;
import com.techscript.spot82.entities.Payment;
import com.techscript.spot82.entities.Spot;
import com.techscript.spot82.exceptions.ClientExceptions;
import com.techscript.spot82.services.ParkingService;
import com.techscript.spot82.services.PaymentService;
import com.techscript.spot82.services.SpotService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/estacionamentos")
@CrossOrigin(origins = {"http://localhost:5173/", "http://localhost:4200/"})
public class ParkingController {

    private ParkingService service;
    private SpotService spotService;
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid Parking parking, BindingResult result) {

        if (result.hasErrors()) {

            Map<String, String> erros = new HashMap<>();
            result.getFieldErrors().forEach(error -> erros.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);

        }

        service.salvar(parking);

        return ResponseEntity.status(HttpStatus.CREATED).body(parking);
    }

    @GetMapping("/vagas")
    public ResponseEntity<List<Spot>> listar() {
        return ResponseEntity.ok().body(spotService.listar());
    }

    @GetMapping("/vagas/{id}")
    public ResponseEntity<Spot> buscarClientePorId(@PathVariable Long id) {
        Spot cliente = spotService.vagaPorId(id);

        if (cliente == null) {
            throw new ClientExceptions("Placa inexistente no sistema! Verifique e tente novamente.");
        }

        return ResponseEntity.ok().body(cliente);
    }

    @GetMapping("/pagamentos")
    public ResponseEntity<List<Payment>> listarPagamentos() {
        return ResponseEntity.ok().body(paymentService.pagamentosDodDia(LocalDateTime.now()));
    }

}
