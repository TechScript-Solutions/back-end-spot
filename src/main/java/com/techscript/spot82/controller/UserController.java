package com.techscript.spot82.controller;

import com.techscript.spot82.dtos.UserDTO;
import com.techscript.spot82.entities.User;
import com.techscript.spot82.services.UserServices;
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
@RequestMapping("/usuarios")
@CrossOrigin(origins = {"http://localhost:5173/", "http://localhost:4200/"})
public class UserController {

    private UserServices userServices;

    @GetMapping
    public ResponseEntity<List<UserDTO>> list() {

        List<UserDTO> usuarios = userServices.list();

        return ResponseEntity.ok().body(usuarios);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid User user, BindingResult result) {

        if (result.hasErrors()) {

            Map<String, String> erros = new HashMap<>();
            result.getFieldErrors().forEach(erro -> erros.put(erro.getField(), erro.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);

        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userServices.save(user));
    }

    @GetMapping("/consulta")
    public ResponseEntity<UserDTO> findByNick(@RequestParam String nickname) {

        var usuario = userServices.findByNick(nickname);
        return ResponseEntity.ok().body(usuario);

    }

    @PutMapping
    public ResponseEntity<User> alterarDados(@RequestParam String nickname, @RequestBody User user) {
        return ResponseEntity.ok().body(user);

    }

    @DeleteMapping
    public ResponseEntity<User> removerUsuario(@RequestParam String nickname) {
        userServices.removeUser(nickname);
        return ResponseEntity.noContent().build();
    }

}