package com.web.puppylink.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.puppylink.model.Board;
import com.web.puppylink.model.FlightTicket;

public interface FlightTicketRepository extends JpaRepository<FlightTicket, String> {
	//출발시간이 지난 flightTicket 
	@Query(value = "SELECT * FROM flightTicket a WHERE DATE(Now()) > DATE(a.depDate)",  nativeQuery = true) 
	List<FlightTicket> getAfterArrive();
}
