package com.techscript.spot82.entities;

import com.techscript.spot82.enums.LateFee;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Data
public class Parking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do estacionamento obrigatório")
    private String name;

    @NotBlank(message = "Insira um CPF ou CNPJ")
    private String document;

    @NotNull(message = "Informe a quantidade de vagas do estacionamento")
    private Integer spot;

    @NotNull(message = "Informe o período máximo de permanência sem taxas (se houver)")
    private Integer period;

    @NotNull(message = "Adicione o valor por período")
    private Double value;

    @Enumerated(EnumType.STRING)
    private LateFee lateFe;

    @Valid
    @OneToOne
    private User user;
    
}