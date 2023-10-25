package com.techscript.spot82.services;

import com.techscript.spot82.entities.Cliente;
import com.techscript.spot82.entities.Vaga;
import com.techscript.spot82.enums.StatusDaVaga;
import com.techscript.spot82.exceptions.ClienteExceptions;
import com.techscript.spot82.respository.ClienteRepository;
import com.techscript.spot82.respository.PagamentoRepository;
import com.techscript.spot82.respository.VagaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class ClienteServices {

    private ClienteRepository clienteRepository;

    private VagaRepository vagaRepository;

    private PagamentoRepository pagamentoRepository;

    private VagaService serviceVaga;

    private EstacionamentoService estacionamentoService;

    public Cliente save(Cliente cliente) {

        if (clienteRepository.existsByPlaca(cliente.getPlaca())) {
            throw new ClienteExceptions("Placa já cadastrada no sistema.");
        }

        serviceVaga.verificaVagaOcupada(cliente.getVaga());

        cliente.setData(LocalDate.now());
        cliente.getPagamento().setPagamento(0.0);

        pagamentoRepository.save(cliente.getPagamento());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        cliente.setHoraEntrada(LocalTime.now().format(formatter));

        Vaga vaga = vagaRepository.findById(cliente.getVaga()).get();
        vaga.setStatusDaVaga(StatusDaVaga.OCUPADA);
        cliente.setVaga(vaga.getVagaDoCliente());
        vaga.setCliente(cliente);

        clienteRepository.save(cliente);
        vagaRepository.save(vaga);

        return cliente;

    }

    public List<Cliente> list() {
        return clienteRepository.findAll();
    }

    public Cliente saida(Cliente cliente) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        cliente.setHoraSaida(LocalTime.now().plusHours(5).plusMinutes(32).format(formatter));

        LocalTime entrada = LocalTime.parse(cliente.getHoraEntrada(), formatter);
        LocalTime saida = LocalTime.parse(cliente.getHoraSaida(), formatter);

        Duration intervalo = Duration.between(entrada, saida);

        LocalTime localTime = LocalTime.of((int) intervalo.toHours(), (int) intervalo.toMinutes() % 60);
        String periodo = localTime.format(formatter);
        cliente.setPeriodo(periodo);

        cliente = estacionamentoService.buscarTaxa(intervalo, cliente);

        return cliente;

    }

    public Cliente recibo(Cliente cliente) {

        vagaRepository.vagaDoCliente(cliente.getId());
        clienteRepository.deleteById(cliente.getId());
        Vaga vaga = vagaRepository.findById(cliente.getVaga()).get();
        vaga.setStatusDaVaga(StatusDaVaga.DISPONIVEL);
        vagaRepository.save(vaga);

        return cliente;
    }

    public Integer clientesTotaisEstacionados() {
        var cliente = clienteRepository.findAll().size();
        return cliente;
    }

    public Cliente buscarPorId(Long id) {

        Cliente cliente = clienteRepository.findById(id).get();

        if (cliente.equals(null)) {
            throw new ClienteExceptions("Cliente não encontrado");
        }

        return cliente;
    }

}
