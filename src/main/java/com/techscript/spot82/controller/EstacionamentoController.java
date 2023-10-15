package com.techscript.spot82.controller;

import com.techscript.spot82.entities.Estacionamento;
import com.techscript.spot82.services.ClienteServices;
import com.techscript.spot82.services.EstacionamentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
@RequestMapping("/estacionamentos")
public class EstacionamentoController {

    private EstacionamentoService service;
    private ClienteServices clienteServices;

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

}
