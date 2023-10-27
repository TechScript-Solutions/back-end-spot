package com.techscript.spot82.services;

import com.techscript.spot82.entities.Mensalista;
import com.techscript.spot82.entities.Vaga;
import com.techscript.spot82.enums.StatusDaVaga;
import com.techscript.spot82.enums.StatusPagamentoMensalista;
import com.techscript.spot82.exceptions.ClienteExceptions;
import com.techscript.spot82.respository.MensalistaRepository;
import com.techscript.spot82.respository.PagamentoRepository;
import com.techscript.spot82.respository.VagaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        mensalista.setPagamentoMensalista(mensalista.getPagamentoMensalista());
        mensalista.setDataDeVencimento(dataFormatada);

        pagamentoRepository.save(mensalista.getPagamentoMensalista());

        Vaga vaga = vagaRepository.findById(mensalista.getVaga()).get();

        vaga.setStatusDaVaga(StatusDaVaga.OCUPADA);
        mensalista.setVaga(vaga.getVagaDoCliente());
        vaga.setMensalista(mensalista);

        mensalistaRepository.save(mensalista);
        vagaRepository.save(vaga);

        return mensalista;

    }

    public List<Mensalista> listar() {
        return mensalistaRepository.findAll();
    }

    public List<Mensalista> mensalistasAtrasados() {
        List<Mensalista> mensalistas = mensalistaRepository.statusPagamento(StatusPagamentoMensalista.ATRASADO);
        return mensalistas;
    }

    public Mensalista pagamentoMensalista(String cpf, Mensalista mensalista) {

        var mensalist = mensalistaRepository.cpf(cpf);

        if (mensalist == null) {
            throw new ClienteExceptions("Mensalista não encontrado");
        }
        mensalista.setId(mensalist.getId());
        mensalista.setDataDeVencimento(LocalDate.now().plusMonths(1).toString());

        BeanUtils.copyProperties(mensalista, mensalist);
        pagamentoRepository.save(mensalist.getPagamentoMensalista());
        mensalistaRepository.save(mensalist);

        return mensalist;
    }

    public void removerMensalista(String cpf) {

        var mensalistaBusca = mensalistaRepository.cpf(cpf);

        var vaga = new Vaga();
        vaga.setId(mensalistaBusca.getVaga());
        vaga.setVagaDoCliente(mensalistaBusca.getVaga());
        vaga.setStatusDaVaga(StatusDaVaga.DISPONIVEL);
        vagaRepository.save(vaga);

        mensalistaRepository.delete(mensalistaBusca);

    }

}
