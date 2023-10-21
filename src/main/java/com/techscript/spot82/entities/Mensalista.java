package com.techscript.spot82.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techscript.spot82.enums.StatusPagamentoMensalista;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

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

    @CPF(message = "Digite um CPF válido")
    @NotBlank(message = "CPF obrigatório")
    private String cpf;

    @NotBlank(message = "Insira a rua")
    private String rua;

    @NotBlank(message = "Insira o bairro")
    private String bairro;

    @NotNull(message = "Insira o número")
    private Integer numero;

    @Enumerated(EnumType.STRING)
    private StatusPagamentoMensalista statusPagamento;

    @Valid
    @OneToOne
    @JoinColumn(name = "vaga_id")
    private Vaga vaga;

    @ManyToOne
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamentoMensalista;

    private String dataDeVencimento;

}
