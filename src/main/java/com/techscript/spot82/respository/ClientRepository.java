package com.techscript.spot82.respository;

import com.techscript.spot82.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findBySpot(Long id);
    boolean existsByPlate(String plate);
    void deleteBySpot(Long spot);

}
