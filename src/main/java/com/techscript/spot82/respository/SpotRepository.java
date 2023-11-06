package com.techscript.spot82.respository;

import com.techscript.spot82.entities.Spot;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SpotRepository extends JpaRepository<Spot, Long> {

    Spot SpotClient(Long spot);
    Spot findByClientId(Long id);
}
