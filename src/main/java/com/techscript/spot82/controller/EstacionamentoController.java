package com.techscript.spot82.controller;

import com.techscript.spot82.entities.Estacionamento;
import com.techscript.spot82.entities.Vaga;
import com.techscript.spot82.exceptions.ClienteExceptions;
import com.techscript.spot82.services.EstacionamentoService;
import com.techscript.spot82.services.VagaService;
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
@RequestMapping("/estacionamentos")
@CrossOrigin(origins = "http://localhost:5173/")
public class EstacionamentoController {

    private EstacionamentoService service;
    private VagaService vagaService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid Estacionamento estacionamento, BindingResult result) {

        if (result.hasErrors()) {

            Map<String, String> erros = new HashMap<>();
            result.getFieldErrors().forEach(error -> erros.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);

        }

        service.salvar(estacionamento);

        return ResponseEntity.status(HttpStatus.CREATED).body(estacionamento);
    }

    @GetMapping("/vagas")
    public ResponseEntity<List<Vaga>> listar() {
        return ResponseEntity.ok().body(vagaService.listar());
    }

    @GetMapping("/vagas/{id}")
    public ResponseEntity<Vaga> buscarClientePorId(@PathVariable Long id) {
        Vaga cliente = vagaService.vagaPorId(id);

        if (cliente == null) {
            throw new ClienteExceptions("Placa inexistente no sistema! Verifique e tente novamente.");
        }

        return ResponseEntity.ok().body(cliente);
    }

}
