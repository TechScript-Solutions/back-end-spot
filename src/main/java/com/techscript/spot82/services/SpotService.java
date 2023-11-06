package com.techscript.spot82.services;

import com.techscript.spot82.entities.Spot;
import com.techscript.spot82.enums.StatusSpot;
import com.techscript.spot82.exceptions.SpotBusyExceptions;
import com.techscript.spot82.respository.SpotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SpotService {

    private SpotRepository spotRepository;

    public void verificaVagaOcupada(Long vaga) {

        Spot verificaSpot = spotRepository.findById(vaga).get();

        if(verificaSpot.getStatusSpot().equals(StatusSpot.OCUPADA)) {
            throw new SpotBusyExceptions("Esta vaga já está ocupada ");
        }

    }

    public List<Spot> listar() {
        return spotRepository.findAll();
    }

    public Spot vagaPorId(Long id) {
        var vaga = spotRepository.findById(id);
        return vaga.get();
    }

    public Spot clientePorId(Long id) {
        var vaga = spotRepository.findByClientId(id);
        return vaga;
    }

}
