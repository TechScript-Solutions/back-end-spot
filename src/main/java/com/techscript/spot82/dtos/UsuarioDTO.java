package com.techscript.spot82.dtos;

import com.techscript.spot82.entities.Usuario;
import com.techscript.spot82.enums.Papel;
import lombok.Data;

import java.util.UUID;

@Data
public class UsuarioDTO {

    private UUID id;
    private String nome;
    private String nickname;
    private String contato;
    private Papel papel;

    public UsuarioDTO() {

    }

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.nickname = usuario.getNickname();
        this.contato = usuario.getContato();
        this.papel = usuario.getPapel();
    }
}