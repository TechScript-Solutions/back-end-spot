package com.techscript.spot82.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techscript.spot82.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByPlaca(String placa);

}
