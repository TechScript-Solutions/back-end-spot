package com.techscript.spot82.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Mensalista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Insira o nome do cliente")
    private String nome;

    @NotBlank(message = "Insira o contato do cliente")
    private String contato;

    @NotBlank(message = "Insira a rua")
    private String rua;

    @NotBlank(message = "Insira o bairro")
    private String bairro;

    @NotBlank(message = "Insira o número")
    private Integer numero;

    @Valid
    private Vaga vaga;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate data;
    
    Pagamento pagamento;
    private String email;

}
