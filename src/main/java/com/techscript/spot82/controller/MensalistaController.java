package com.techscript.spot82.controller;

import com.techscript.spot82.entities.Mensalista;
import com.techscript.spot82.services.MensalistaService;
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
public class MensalistaController {

    private final MensalistaService mensalistaService;

    @PostMapping
    public ResponseEntity<Mensalista> criar(@Valid @RequestBody Mensalista mensalista, BindingResult result) {

        if (result.hasErrors()) {

            Map<String, String> erros = new HashMap<>();
            result.getFieldErrors().forEach(error -> erros.put(error.getField(), error.getDefaultMessage()));


        }

        Mensalista novoMensalista = mensalistaService.salvar(mensalista);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMensalista);
    }

    @GetMapping
    public ResponseEntity<List<Mensalista>> listar() {
        List<Mensalista> listaMensalistas = mensalistaService.listar();
        return ResponseEntity.ok().body(listaMensalistas);
    }

    @GetMapping("/atrasados")
    public ResponseEntity<List<Mensalista>> mensalistasAtrasados() {
        return ResponseEntity.ok().body(mensalistaService.mensalistasAtrasados());
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> removerMensalista(@PathVariable String cpf) {
        mensalistaService.removerMensalista(cpf);
        return ResponseEntity.noContent().build();
    }

}
