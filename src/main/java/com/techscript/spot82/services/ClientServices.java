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

        serviceVaga.checkSpotBusy(client.getSpot());

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
    public Client exit(Client client, String methodPayment) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        client.setHourExit(LocalTime.now().format(formatter));

        LocalTime entry = LocalTime.parse(client.getHourEntry(), formatter);
        LocalTime exit = LocalTime.parse(client.getHourExit(), formatter);

        Duration interval = Duration.between(entry, exit);

        LocalTime localTime = LocalTime.of((int) interval.toHours(), (int) interval.toMinutes() % 60);
        String period = localTime.format(formatter);
        client.setPeriod(period);

        client = parkingService.calcularValor(interval, client);

        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        var payment = new Payment();
        payment.setPayment(client.getPayment());
        payment.setMethodPayment(methodPayment);
        payment.setDate(formater.format(LocalDate.now()));
        payment.setClient(null);

        paymentRepository.save(payment);

        Spot spot = spotRepository.findById(client.getSpot()).get();
        spot.setStatusSpot(StatusSpot.DISPONIVEL);
        spot.setClient(null);

        spotRepository.save(spot);
        clientRepository.deleteBySpot(client.getSpot());

        return client;

    }

    public Integer allClientsParked() {
        var cliente = clientRepository.findAll().size();
        return cliente;
    }

    public Client findById(Long id) {

        Client client = clientRepository.findById(id).get();

        if (client.equals(null)) {
            throw new ClientExceptions("Cliente não encontrado");
        }

        return client;
    }

}
