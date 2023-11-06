package com.techscript.spot82.entities;

import com.techscript.spot82.enums.StatusPaymentMonthlypayer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Data
public class MonthlyPayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Insira o nome do cliente")
    private String name;
    
    @NotBlank(message = "Insira o contato do cliente")
    private String contact;

    @CPF(message = "Digite um CPF válido")
    @NotBlank(message = "CPF obrigatório")
    private String cpf;

    @NotBlank(message = "Insira a rua")
    private String street;

    @NotBlank(message = "Insira o bairro")
    private String district;

    @NotNull(message = "Insira o número")
    private Integer numero;

    @Enumerated(EnumType.STRING)
    private StatusPaymentMonthlypayer statusPayment;

    private String dueDate;

    @NotNull(message = "Insira a vaga")
    private Long spot;

    @ManyToOne
    @JoinColumn(name = "pagamento_id")
    private Payment paymentMensalista;


}
