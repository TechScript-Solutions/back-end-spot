package com.techscript.spot82.services;

import com.techscript.spot82.entities.Cliente;
import com.techscript.spot82.entities.Pagamento;
import com.techscript.spot82.entities.Vaga;
import com.techscript.spot82.enums.StatusDaVaga;
import com.techscript.spot82.exceptions.ClienteExceptions;
import com.techscript.spot82.respository.ClienteRepository;
import com.techscript.spot82.respository.PagamentoRepository;
import com.techscript.spot82.respository.VagaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Cliente saida(Cliente cliente, String formaDePagamento) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        cliente.setHoraSaida(LocalTime.now().format(formatter));

        LocalTime entrada = LocalTime.parse(cliente.getHoraEntrada(), formatter);
        LocalTime saida = LocalTime.parse(cliente.getHoraSaida(), formatter);

        Duration intervalo = Duration.between(entrada, saida);

        LocalTime localTime = LocalTime.of((int) intervalo.toHours(), (int) intervalo.toMinutes() % 60);
        String periodo = localTime.format(formatter);
        cliente.setPeriodo(periodo);

        cliente = estacionamentoService.calcularValor(intervalo, cliente);

        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        var pagamento = new Pagamento();
        System.out.println(formaDePagamento);
        pagamento.setPagamento(cliente.getPagamento());
        pagamento.setFormaDePagamento(formaDePagamento);
        pagamento.setData(formater.format(LocalDate.now()));
        pagamento.setCliente(null);

        pagamentoRepository.save(pagamento);

        Vaga vaga = vagaRepository.findById(cliente.getVaga()).get();
        vaga.setStatusDaVaga(StatusDaVaga.DISPONIVEL);
        vaga.setCliente(null);

        vagaRepository.save(vaga);
        clienteRepository.deleteByVaga(cliente.getVaga());

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
