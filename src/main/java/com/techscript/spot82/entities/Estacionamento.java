package com.techscript.spot82.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Estacionamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome do estacionamento obrigatório")
    private String nome;
    @NotBlank(message = "Insira um CPF ou CNPJ")
    private String cnpj;
    @NotNull(message = "Informe a quantidade de vagas do estacionamento")
    private Integer vaga;
    @Valid
    @OneToOne
    private Usuario usuario;
    
}