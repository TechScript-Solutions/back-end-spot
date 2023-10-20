package com.techscript.spot82.respository;

import com.techscript.spot82.entities.Mensalista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MensalistaRepository extends JpaRepository<Mensalista, UUID> {

}
