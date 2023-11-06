package com.techscript.spot82.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techscript.spot82.enums.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do cliente obrigatório")
    private String name;

    @NotBlank(message = "Veículo obrigatório")
    private String vehicle;

    @Enumerated(EnumType.STRING)
    private Type type;

    @NotBlank(message = "Placa obrigatória")
    private String plate;

    @NotNull(message = "Insira a vaga")
    private Long spot;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    private String methodPayment;

    private String payment;

    private String hourEntry;

    private String hourExit;

    private String period;

}
