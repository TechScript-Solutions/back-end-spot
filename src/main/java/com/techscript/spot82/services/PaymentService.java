package com.techscript.spot82.services;

import com.techscript.spot82.entities.Payment;
import com.techscript.spot82.respository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {

    private PaymentRepository paymentRepository;

    public List<Payment> pagamentosDodDia(LocalDateTime registrosDoDia) {

        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String data = formater.format(registrosDoDia);

        return paymentRepository.findByDate(data);

    }

}
