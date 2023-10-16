package com.techscript.spot82.services;

import com.techscript.spot82.entities.Mensalista;
import com.techscript.spot82.entities.Vaga;
import com.techscript.spot82.enums.Status;
import com.techscript.spot82.respository.MensalistaRepository;
import com.techscript.spot82.respository.PagamentoRepository;
import com.techscript.spot82.respository.VagaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MensalistaService {

    private final MensalistaRepository mensalistaRepository;
    private final VagaRepository vagaRepository;
    private final PagamentoRepository pagamentoRepository;

    public Mensalista salvar(Mensalista mensalista) {

        mensalista.getVaga().setStatus(Status.OCUPADA);
        mensalista.setPagamentoMensalista(mensalista.getPagamentoMensalista());
        mensalista.setDataDeVencimento(LocalDateTime.now().plusMonths(1));

        Vaga vaga = vagaRepository.findById(mensalista.getVaga().getVagaDoCliente()).get();
        vaga.setStatus(Status.OCUPADA);
        mensalista.setVaga(vaga);
        vagaRepository.save(vaga);

        pagamentoRepository.save(mensalista.getPagamentoMensalista());
        mensalistaRepository.save(mensalista);

        return mensalista;

    }

    public List<Mensalista> listar() {
        return mensalistaRepository.findAll();
    }

}
