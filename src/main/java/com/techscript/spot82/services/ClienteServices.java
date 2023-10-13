package com.techscript.spot82.services;

import com.techscript.spot82.entities.Cliente;
import com.techscript.spot82.entities.Vaga;
import com.techscript.spot82.enums.Status;
import com.techscript.spot82.respository.ClienteRepository;
import com.techscript.spot82.respository.PagamentoRepository;
import com.techscript.spot82.respository.VagaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
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


    public Cliente save(Cliente cliente) {

        cliente.setData(LocalDate.now());
        cliente.getPagamento().setPagamento(0.0);

        pagamentoRepository.save(cliente.getPagamento());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        cliente.setHoraEntrada(LocalTime.now().format(formatter));

        Vaga vaga = vagaRepository.findById(cliente.getVagaCliente().getQuantidadeDeVagas()).get();
        vaga.setStatus(Status.OCUPADA);
        cliente.setVagaCliente(vaga);

        vagaRepository.save(vaga);

        return clienteRepository.save(cliente);

    }

    public List<Cliente> list() {

        return clienteRepository.findAll();

    }

    public Cliente findByPlate(String placa) {

        return clienteRepository.findByPlaca(placa);

    }

    public Vaga findById(Long id) {

        return vagaRepository.quantidadeDeVagas(id);

    }

    public Cliente saida(Cliente cliente) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        cliente.setHoraSaida(LocalTime.now().format(formatter));

        LocalTime entrada = LocalTime.parse(cliente.getHoraEntrada(), formatter);
        LocalTime saida = LocalTime.parse(cliente.getHoraSaida(), formatter);

        Duration intervalo = Duration.between(entrada, saida);

        LocalTime localTime = LocalTime.of((int) intervalo.toHours(), (int) intervalo.toMinutes() % 60);
        String periodo = localTime.format(formatter);
        cliente.setPeriodo(periodo);

        Double total = intervalo.toMinutes() % 60 * 0.0233333333333333;

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String totalFormatter = decimalFormat.format(total);

        if (intervalo.toMinutes() % 60 <= 300) {
            cliente.getPagamento().setPagamento(7.0);
        } else {
            cliente.getPagamento().setPagamento(Double.parseDouble(totalFormatter));
        }

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        cliente.getPagamento().setData(LocalDate.now().format(formatterDate));
        pagamentoRepository.save(cliente.getPagamento());

        return cliente;

    }

    public Cliente recibo(Cliente cliente) {

        findById(cliente.getId());
        clienteRepository.deleteById(cliente.getId());
        Vaga vaga = vagaRepository.findById(cliente.getVagaCliente().getQuantidadeDeVagas()).get();
        vaga.setStatus(Status.DISPONIVEL);
        vagaRepository.save(vaga);

        return cliente;
    }

}
