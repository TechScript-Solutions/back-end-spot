package com.techscript.spot82.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techscript.spot82.enums.FormaDePagamento;
import com.techscript.spot82.enums.Tipo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do cliente obrigatório")
    private String nome;

    @NotBlank(message = "Veículo obrigatório")
    private String veiculo;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @NotBlank(message = "Placa obrigatória")
    private String placa;

    @NotNull(message = "Insira a vaga")
    private Long vaga;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate data;

    private String formaDePagamento;

    private String pagamento;

    private String horaEntrada;

    private String horaSaida;

    private String periodo;

}
