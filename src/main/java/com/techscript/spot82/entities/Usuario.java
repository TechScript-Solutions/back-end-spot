package com.techscript.spot82.entities;

import com.techscript.spot82.enums.Papel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome obrigatório")
    private String nome;

    @NotBlank(message = "E-mail obrigatório")
    @Email(message = "Informe um e-mail válido")
    private String email;

    @NotBlank(message = "Defina sua senha")
    private String password;

    @NotBlank(message = "Defina a senha de confirmação")
    private String confirmaPassword;

    @Enumerated(EnumType.STRING)
    private Papel papel;

}
