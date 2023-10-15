package com.techscript.spot82.entities;

import com.techscript.spot82.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Defina a vaga do cliente")
    private Long vagaDoCliente;

    @Enumerated(EnumType.STRING)
    private Status status;

}
