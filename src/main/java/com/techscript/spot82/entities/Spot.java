package com.techscript.spot82.entities;

import com.techscript.spot82.enums.StatusSpot;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Defina a vaga do cliente")
    private Long spotClient;

    @Enumerated(EnumType.STRING)
    private StatusSpot statusSpot;

    @Valid
    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Valid
    @OneToOne
    @JoinColumn(name = "monthlypayer_id")
    private MonthlyPayer monthlyPayer;

}
