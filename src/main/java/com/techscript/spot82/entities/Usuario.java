package com.techscript.spot82.entities;

import com.techscript.spot82.enums.Papel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Nome obrigatório")
    private String nome;

    @NotBlank(message = "Nickname obrigatório")
    private String nickname;

    @NotBlank(message = "Defina sua senha")
    private String password;

    @NotBlank(message = "Contato obrigatório")
    private String contato;

    @NotBlank(message = "Defina a senha de confirmação")
    private String confirmaPassword;

    @Enumerated(EnumType.STRING)
    private Papel papel;

}
