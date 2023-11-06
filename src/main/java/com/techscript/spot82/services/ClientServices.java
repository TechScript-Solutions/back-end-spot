package com.techscript.spot82.services;

import com.techscript.spot82.entities.Client;
import com.techscript.spot82.entities.Payment;
import com.techscript.spot82.entities.Spot;
import com.techscript.spot82.enums.StatusSpot;
import com.techscript.spot82.exceptions.ClientExceptions;
import com.techscript.spot82.respository.ClientRepository;
import com.techscript.spot82.respository.PaymentRepository;
import com.techscript.spot82.respository.SpotRepository;
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
public class ClientServices {

    private ClientRepository clientRepository;

    private SpotRepository spotRepository;

    private PaymentRepository paymentRepository;

    private SpotService serviceVaga;

    private ParkingService parkingService;

    public Client save(Client client) {

        if (clientRepository.existsByPlate(client.getPlate())) {
            throw new ClientExceptions("Placa já cadastrada no sistema.");
        }

        serviceVaga.verificaVagaOcupada(client.getSpot());

        client.setDate(LocalDate.now());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        client.setHourEntry(LocalTime.now().format(formatter));

        Spot spot = spotRepository.findById(client.getSpot()).get();
        spot.setStatusSpot(StatusSpot.OCUPADA);
        client.setSpot(spot.getSpotClient());
        spot.setClient(client);

        clientRepository.save(client);
        spotRepository.save(spot);

        return client;

    }

    public List<Client> list() {
        return clientRepository.findAll();
    }

    @Transactional
    public Client saida(Client client, String formaDePagamento) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        client.setHourExit(LocalTime.now().format(formatter));

        LocalTime entrada = LocalTime.parse(client.getHourEntry(), formatter);
        LocalTime saida = LocalTime.parse(client.getHourExit(), formatter);

        Duration intervalo = Duration.between(entrada, saida);

        LocalTime localTime = LocalTime.of((int) intervalo.toHours(), (int) intervalo.toMinutes() % 60);
        String periodo = localTime.format(formatter);
        client.setPeriod(periodo);

        client = parkingService.calcularValor(intervalo, client);

        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        var pagamento = new Payment();
        pagamento.setPayment(client.getPayment());
        pagamento.setMethodPayment(formaDePagamento);
        pagamento.setDate(formater.format(LocalDate.now()));
        pagamento.setClient(null);

        paymentRepository.save(pagamento);

        Spot spot = spotRepository.findById(client.getSpot()).get();
        spot.setStatusSpot(StatusSpot.DISPONIVEL);
        spot.setClient(null);

        spotRepository.save(spot);
        clientRepository.deleteBySpot(client.getSpot());

        return client;

    }

    public Integer clientesTotaisEstacionados() {
        var cliente = clientRepository.findAll().size();
        return cliente;
    }

    public Client buscarPorId(Long id) {

        Client client = clientRepository.findById(id).get();

        if (client.equals(null)) {
            throw new ClientExceptions("Cliente não encontrado");
        }

        return client;
    }

}
