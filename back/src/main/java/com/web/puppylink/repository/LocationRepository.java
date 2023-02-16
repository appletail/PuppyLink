package com.web.puppylink.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.web.puppylink.model.Location;

public interface LocationRepository extends JpaRepository<Location,Integer>{
    @Modifying
    @Query(value="INSERT INTO Location(ticketNo, flight, tmpLat, tmpLng, tmpDir) VALUES(?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
	int mSave(String ticketNo , @NotNull String flight, Double tmpLat , Double tmpLng, Double tmpDir);
    
    List<Location> findAllByFlight(String flight);
    
    
    @Modifying
    @Query(value="DELETE FROM flightTicket where ticketNo = ?1", nativeQuery = true)
	int deleteTicket(String ticketNo);

}
