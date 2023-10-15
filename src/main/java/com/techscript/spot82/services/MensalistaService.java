package com.techscript.spot82.services;

import com.techscript.spot82.entities.Mensalista;
import com.techscript.spot82.enums.Status;
import com.techscript.spot82.respository.MensalistaRepository;
import com.techscript.spot82.respository.PagamentoRepository;
import com.techscript.spot82.respository.VagaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class MensalistaService {

    private final MensalistaRepository mensalistaRepository;
    private final VagaRepository vagaRepository;
    private final PagamentoRepository pagamentoRepository;

    public Mensalista salvar(Mensalista mensalista) {

        mensalista.getVaga().setStatus(Status.OCUPADA);
        mensalista.setData(LocalDate.now());
        mensalista.setPagamentoMensalista(mensalista.getPagamentoMensalista());

        pagamentoRepository.save(mensalista.getPagamentoMensalista());
        vagaRepository.save(mensalista.getVaga());

        return mensalistaRepository.save(mensalista);
    }

    public List<Mensalista> listar() {
        return mensalistaRepository.findAll();
    }

}
