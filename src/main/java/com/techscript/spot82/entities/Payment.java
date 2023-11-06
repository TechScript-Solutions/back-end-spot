package com.techscript.spot82.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    private String methodPayment;

    private String payment;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
