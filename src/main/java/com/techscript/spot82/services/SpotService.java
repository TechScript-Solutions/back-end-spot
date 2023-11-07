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

    public void checkSpotBusy(Long vaga) {

        Spot checkSpot = spotRepository.findById(vaga).get();

        if(checkSpot.getStatusSpot().equals(StatusSpot.OCUPADA)) {
            throw new SpotBusyExceptions("Esta vaga já está ocupada ");
        }

    }

    public List<Spot> list() {
        return spotRepository.findAll();
    }

    public Spot SpotById(Long id) {
        var spot = spotRepository.findById(id);
        return spot.get();
    }

    public Spot clientById(Long id) {
        var spot = spotRepository.findByClientId(id);
        return spot;
    }

}
