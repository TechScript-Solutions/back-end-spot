package com.techscript.spot82.entities;

import com.techscript.spot82.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Nome obrigatório")
    private String name;

    @NotBlank(message = "Nickname obrigatório")
    private String nickname;

    @NotBlank(message = "Defina sua senha")
    private String password;

    @NotBlank(message = "Contato obrigatório")
    private String contact;

    @NotBlank(message = "Defina a senha de confirmação")
    private String confirmPassword;

    @Enumerated(EnumType.STRING)
    private Roles roles;

}
