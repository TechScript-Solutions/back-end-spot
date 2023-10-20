package com.techscript.spot82.services;

import com.techscript.spot82.entities.Cliente;
import com.techscript.spot82.entities.Vaga;
import com.techscript.spot82.enums.Status;
import com.techscript.spot82.exceptions.VagaOcupadaExceptions;
import com.techscript.spot82.respository.VagaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VagaService {

    private VagaRepository vagaRepository;

    public void verificaVagaOcupada(Vaga vaga) {

        Vaga verificaVaga = vagaRepository.findById(vaga.getVagaDoCliente()).get();

        if(verificaVaga.getStatus().equals(Status.OCUPADA)) {
            throw new VagaOcupadaExceptions("Esta vaga já está ocupada ");
        }

    }

    public List<Vaga> listar() {
        return vagaRepository.findAll();
    }

}
