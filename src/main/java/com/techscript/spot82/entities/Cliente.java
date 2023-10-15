package com.techscript.spot82.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.Valid;
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
    @NotBlank(message = "Placa obrigatória")
    private String placa;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate data;
    private String horaEntrada;
    private String horaSaida;
    private String periodo;
    @ManyToOne
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamento;
    @Valid
    @ManyToOne
    @JoinColumn(name = "vaga_id")
    private Vaga vaga;

}
