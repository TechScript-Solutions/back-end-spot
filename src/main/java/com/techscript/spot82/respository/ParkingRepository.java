package com.techscript.spot82.respository;

import com.techscript.spot82.entities.Parking;
import com.techscript.spot82.enums.LateFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

    Parking findBylateFe(LateFee rate);
    @Query("SELECT e.value FROM Parking e")
    Double value();

}
