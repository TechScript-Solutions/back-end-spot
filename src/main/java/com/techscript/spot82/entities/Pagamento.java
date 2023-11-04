package com.techscript.spot82.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.techscript.spot82.enums.FormaDePagamento;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String data;

    private String formaDePagamento;

    private String pagamento;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

}
