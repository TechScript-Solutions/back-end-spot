package com.techscript.spot82.services;

import com.techscript.spot82.entities.Client;
import com.techscript.spot82.entities.Parking;
import com.techscript.spot82.entities.Spot;
import com.techscript.spot82.enums.StatusSpot;
import com.techscript.spot82.respository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class ParkingService {

    private ParkingRepository parkingRepository;
    private UsuarioRespository usuarioRespository;
    private SpotRepository spotRepository;
    private PaymentRepository paymentRepository;
    private ClientRepository clientRepository;

    public Client calcularValor(Duration intervalo, Client client) {

//        Estacionamento verificaTaxa = estacionamentoRepository.findBytaxaPorAtraso(TaxaPorAtraso.COM_TAXA);
        boolean taxar = false;
        Double taxa = parkingRepository.value();
        Double aplicarTaxa = taxa / 60;

        if (taxar == true) {

            taxa += intervalo.toMinutes() % 60 * aplicarTaxa;

            DecimalFormat decimalFormat = new DecimalFormat("#,00");
            String totalFormatter = decimalFormat.format(taxa);

            client.setPayment(totalFormatter);

        } else {

            DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            client.setPayment(taxa.toString());

        }

        clientRepository.save(client);

        return client;
    }

    public Parking salvar(Parking parking) {

        usuarioRespository.save(parking.getUser());

        for (long i = 1; i <= parking.getSpot(); i++) {
            Spot spot = new Spot();
            spot.setSpotClient(i);
            spot.setStatusSpot(StatusSpot.DISPONIVEL);
            spotRepository.save(spot);
        }

        Parking spot = parkingRepository.save(parking);

        return spot;
    }


    public Client findByVaga(Long id) {
        return clientRepository.findBySpot(id).get();
    }

}
