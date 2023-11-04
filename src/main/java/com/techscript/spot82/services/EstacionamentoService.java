package com.techscript.spot82.services;

import com.techscript.spot82.entities.Cliente;
import com.techscript.spot82.entities.Estacionamento;
import com.techscript.spot82.entities.Vaga;
import com.techscript.spot82.enums.StatusDaVaga;
import com.techscript.spot82.respository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class EstacionamentoService {

    private EstacionamentoRepository estacionamentoRepository;
    private UsuarioRespository usuarioRespository;
    private VagaRepository vagaRepository;
    private PagamentoRepository pagamentoRepository;
    private ClienteRepository clienteRepository;

    public Cliente calcularValor(Duration intervalo, Cliente cliente) {

//        Estacionamento verificaTaxa = estacionamentoRepository.findBytaxaPorAtraso(TaxaPorAtraso.COM_TAXA);
        boolean taxar = false;
        Double taxa = estacionamentoRepository.valor();
        Double aplicarTaxa = taxa / 60;

        if (taxar == true) {

            taxa += intervalo.toMinutes() % 60 * aplicarTaxa;

            DecimalFormat decimalFormat = new DecimalFormat("#,00");
            String totalFormatter = decimalFormat.format(taxa);

            cliente.setPagamento(totalFormatter);

        } else {

            DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            cliente.setPagamento(taxa.toString());

        }

        clienteRepository.save(cliente);

        return cliente;
    }

    public Estacionamento salvar(Estacionamento estacionamento) {

        usuarioRespository.save(estacionamento.getUsuario());

        for (long i = 1; i <= estacionamento.getVaga(); i++) {
            Vaga vaga = new Vaga();
            vaga.setVagaDoCliente(i);
            vaga.setStatusDaVaga(StatusDaVaga.DISPONIVEL);
            vagaRepository.save(vaga);
        }

        Estacionamento spot = estacionamentoRepository.save(estacionamento);

        return spot;
    }


    public Cliente findByVaga(Long id) {
        return clienteRepository.findByVaga(id).get();
    }

}
