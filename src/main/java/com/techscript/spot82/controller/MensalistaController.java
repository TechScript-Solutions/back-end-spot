package com.techscript.spot82.controller;

import com.techscript.spot82.entities.Mensalista;
import com.techscript.spot82.services.MensalistaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/mensalistas")
public class MensalistaController {

    private final MensalistaService mensalistaService;

    @PostMapping
    public ResponseEntity<Mensalista> criar(@Valid @RequestBody Mensalista mensalista) {
        Mensalista novoMensalista = mensalistaService.salvar(mensalista);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMensalista);
    }

    @GetMapping
    public ResponseEntity<List<Mensalista>> listar() {
        List<Mensalista> listaMensalistas = mensalistaService.listar();
        return ResponseEntity.ok().body(listaMensalistas);
    }

}
