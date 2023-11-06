package com.techscript.spot82.services;

import com.techscript.spot82.entities.MonthlyPayer;
import com.techscript.spot82.entities.Spot;
import com.techscript.spot82.enums.StatusSpot;
import com.techscript.spot82.enums.StatusPaymentMonthlypayer;
import com.techscript.spot82.exceptions.ClientExceptions;
import com.techscript.spot82.respository.MensalistaRepository;
import com.techscript.spot82.respository.PaymentRepository;
import com.techscript.spot82.respository.SpotRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class MonthlypayerService {

    private final MensalistaRepository mensalistaRepository;
    private final SpotRepository spotRepository;
    private final PaymentRepository paymentRepository;
    private final SpotService spotService;

    public MonthlyPayer salvar(MonthlyPayer monthlyPayer) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime data = LocalDateTime.now().plusMonths(1);
        String dataFormatada = data.format(formatter);

        spotService.verificaVagaOcupada(monthlyPayer.getSpot());

        monthlyPayer.setPaymentMensalista(monthlyPayer.getPaymentMensalista());
        monthlyPayer.setDueDate(dataFormatada);

        paymentRepository.save(monthlyPayer.getPaymentMensalista());

        Spot spot = spotRepository.findById(monthlyPayer.getSpot()).get();

        spot.setStatusSpot(StatusSpot.OCUPADA);
        monthlyPayer.setSpot(spot.getSpotClient());
        spot.setMonthlyPayer(monthlyPayer);

        mensalistaRepository.save(monthlyPayer);
        spotRepository.save(spot);

        return monthlyPayer;

    }

    public List<MonthlyPayer> listar() {
        return mensalistaRepository.findAll();
    }

    public List<MonthlyPayer> mensalistasAtrasados() {
        List<MonthlyPayer> monthlyPayers = mensalistaRepository.statusPayment(StatusPaymentMonthlypayer.ATRASADO);
        return monthlyPayers;
    }

    public MonthlyPayer pagamentoMensalista(String cpf, MonthlyPayer monthlyPayer) {

        var mensalist = mensalistaRepository.cpf(cpf);

        if (mensalist == null) {
            throw new ClientExceptions("Mensalista não encontrado");
        }
        monthlyPayer.setId(mensalist.getId());
        monthlyPayer.setDueDate(LocalDate.now().plusMonths(1).toString());

        BeanUtils.copyProperties(monthlyPayer, mensalist);
        paymentRepository.save(mensalist.getPaymentMensalista());
        mensalistaRepository.save(mensalist);

        return mensalist;
    }

    public void removerMensalista(String cpf) {

        var mensalistaBusca = mensalistaRepository.cpf(cpf);

        var vaga = new Spot();
        vaga.setId(mensalistaBusca.getSpot());
        vaga.setSpotClient(mensalistaBusca.getSpot());
        vaga.setStatusSpot(StatusSpot.DISPONIVEL);
        spotRepository.save(vaga);

        mensalistaRepository.delete(mensalistaBusca);

    }

}
