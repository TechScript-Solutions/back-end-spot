package com.techscript.spot82.services;

import com.techscript.spot82.entities.Mensalista;
import com.techscript.spot82.entities.Vaga;
import com.techscript.spot82.enums.StatusDaVaga;
import com.techscript.spot82.enums.StatusPagamentoMensalista;
import com.techscript.spot82.respository.MensalistaRepository;
import com.techscript.spot82.respository.PagamentoRepository;
import com.techscript.spot82.respository.VagaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class MensalistaService {

    private final MensalistaRepository mensalistaRepository;
    private final VagaRepository vagaRepository;
    private final PagamentoRepository pagamentoRepository;
    private final VagaService vagaService;

    public Mensalista salvar(Mensalista mensalista) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime data = LocalDateTime.now().plusMonths(1);
        String dataFormatada = data.format(formatter);

        vagaService.verificaVagaOcupada(mensalista.getVaga());

        mensalista.getVaga().setStatusDaVaga(StatusDaVaga.OCUPADA);
        mensalista.setPagamentoMensalista(mensalista.getPagamentoMensalista());
        mensalista.setDataDeVencimento(dataFormatada);

        Vaga vaga = vagaRepository.findById(mensalista.getVaga().getVagaDoCliente()).get();
        vaga.setStatusDaVaga(StatusDaVaga.OCUPADA);
        mensalista.setVaga(vaga);
        vagaRepository.save(vaga);

        pagamentoRepository.save(mensalista.getPagamentoMensalista());
        mensalistaRepository.save(mensalista);

        return mensalista;

    }

    public List<Mensalista> listar() {
        return mensalistaRepository.findAll();
    }

    public List<Mensalista> mensalistasAtrasados() {
        List<Mensalista> mensalistas = mensalistaRepository.statusPagamento(StatusPagamentoMensalista.ATRASADO);
        return mensalistas;
    }

}
