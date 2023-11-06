package com.techscript.spot82.controller;

//import com.techscript.spot82.configuracao.GravarDados;

import com.techscript.spot82.entities.Client;
import com.techscript.spot82.exceptions.ClientExceptions;
import com.techscript.spot82.services.ClientServices;
import com.techscript.spot82.services.ParkingService;
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
public class ClientController {

    private ClientServices clientServices;

    private ParkingService parkingService;

    // private GravarDados gravarDados;

    @GetMapping
    public ResponseEntity<List<Client>> listar() {

        List<Client> clients = clientServices.list();

        if (clients.isEmpty()) {
            throw new ClientExceptions("Não há clientes no momento.");
        }

        return ResponseEntity.ok().body(clients);

    }

    @PostMapping
    public ResponseEntity<Client> salvar(@RequestBody @Valid Client client, BindingResult result) {

        if (result.hasErrors()) {

            Map<String, String> erros = new HashMap<>();
            result.getFieldErrors().forEach(error -> erros.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().build();

        }

        clientServices.save(client);

        return ResponseEntity.status(HttpStatus.CREATED).body(client);

    }

    @DeleteMapping("/recibo/{id}/{formaDePagamento}")
    public ResponseEntity<?> finalizar(@PathVariable Long id, @PathVariable String formaDePagamento) {

        Client client = parkingService.findByVaga(id);

        if (client == null) {
            throw new ClientExceptions("Placa inexistente no sistema! Verifique e tente novamente.");
        }

        clientServices.saida(client, formaDePagamento);

        return ResponseEntity.status(HttpStatus.OK).body(client);
    }

    @GetMapping("/totais")
    public ResponseEntity<Integer> clientesTotais() {
        return ResponseEntity.ok().body(clientServices.clientesTotaisEstacionados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> buscarPorId(@PathVariable Long id) {
        var cliente = clientServices.buscarPorId(id);
        return ResponseEntity.ok().body(cliente);
    }

}
