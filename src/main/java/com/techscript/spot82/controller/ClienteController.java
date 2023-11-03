package com.techscript.spot82.controller;

//import com.techscript.spot82.configuracao.GravarDados;

import com.techscript.spot82.entities.Cliente;
import com.techscript.spot82.exceptions.ClienteExceptions;
import com.techscript.spot82.services.ClienteServices;
import com.techscript.spot82.services.EstacionamentoService;
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
@RequestMapping("/clientes")
@CrossOrigin(origins = {"http://localhost:5173/", "http://localhost:4200/"})
public class ClienteController {

    private ClienteServices clienteServices;

    private EstacionamentoService estacionamentoService;

    // private GravarDados gravarDados;

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {

        List<Cliente> clientes = clienteServices.list();

        if (clientes.isEmpty()) {
            throw new ClienteExceptions("Não há clientes no momento.");
        }

        return ResponseEntity.ok().body(clientes);

    }

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody @Valid Cliente cliente, BindingResult result) {

        if (result.hasErrors()) {

            Map<String, String> erros = new HashMap<>();
            result.getFieldErrors().forEach(error -> erros.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().build();

        }

        clienteServices.save(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);

    }

    @DeleteMapping("/recibo/{id}")
    public ResponseEntity<?> finalizar(@PathVariable Long id) {

        Cliente cliente = estacionamentoService.findByVaga(id);

        if (cliente == null) {
            throw new ClienteExceptions("Placa inexistente no sistema! Verifique e tente novamente.");
        }

        clienteServices.saida(cliente);
        clienteServices.recibo(cliente);

        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @GetMapping("/totais")
    public ResponseEntity<Integer> clientesTotais() {
        return ResponseEntity.ok().body(clienteServices.clientesTotaisEstacionados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        var cliente = clienteServices.buscarPorId(id);
        return ResponseEntity.ok().body(cliente);
    }

}
