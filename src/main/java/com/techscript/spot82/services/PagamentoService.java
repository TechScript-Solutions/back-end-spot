package com.techscript.spot82.services;

import com.techscript.spot82.entities.Pagamento;
import com.techscript.spot82.respository.PagamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class PagamentoService {

    private PagamentoRepository pagamentoRepository;

    public List<Pagamento> pagamentosDodDia(LocalDateTime registrosDoDia) {

        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String data = formater.format(registrosDoDia);

        return pagamentoRepository.findByData(data);

    }

}
