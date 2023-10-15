package com.techscript.spot82.dtos;

import com.techscript.spot82.entities.Usuario;
import com.techscript.spot82.enums.Papel;
import lombok.Data;

@Data
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    private Papel papel;

    public UsuarioDTO() {

    }

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.papel = usuario.getPapel();
    }
}