package com.techscript.spot82.controller;

import com.techscript.spot82.dtos.UsuarioDTO;
import com.techscript.spot82.entities.Usuario;
import com.techscript.spot82.services.UsuarioServices;
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
public class UsuarioController {

    private UsuarioServices usuarioServices;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> list() {

        List<UsuarioDTO> usuarios = usuarioServices.listar();

        return ResponseEntity.ok().body(usuarios);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid Usuario usuario, BindingResult result) {

        if (result.hasErrors()) {

            Map<String, String> erros = new HashMap<>();
            result.getFieldErrors().forEach(erro -> erros.put(erro.getField(), erro.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);

        }

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioServices.save(usuario));
    }

    @GetMapping("/consulta")
    public ResponseEntity<UsuarioDTO> buscarPorNick(@RequestParam String nickname) {

        var usuario = usuarioServices.buscarPorNick(nickname);
        return ResponseEntity.ok().body(usuario);

    }

    @PutMapping
    public ResponseEntity<Usuario> alterarDados(@RequestParam String nickname, @RequestBody Usuario usuario) {

        var usuarioAlterado = usuarioServices.alterarDados(nickname, usuario);

        return ResponseEntity.ok().body(usuario);

    }

    @DeleteMapping
    public ResponseEntity<Usuario> removerUsuario(@RequestParam String nickname) {
        usuarioServices.removerUsuario(nickname);
        return ResponseEntity.noContent().build();
    }

}