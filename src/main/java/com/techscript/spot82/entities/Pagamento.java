package com.techscript.spot82.entities;

import com.techscript.spot82.enums.FormaDePagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String data;

    @Enumerated(EnumType.STRING)
    private FormaDePagamento formaDePagamento;

    private Double pagamento;

}
