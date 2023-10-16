package com.techscript.spot82.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private String email;

    @NotBlank(message = "Insira a rua")
    private String rua;

    @NotBlank(message = "Insira o bairro")
    private String bairro;

    @NotNull(message = "Insira o número")
    private Integer numero;

    @Valid
    @OneToOne
    @JoinColumn(name = "vaga_id")
    private Vaga vaga;

    @ManyToOne
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamentoMensalista;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime dataDeVencimento;

}
