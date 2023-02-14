package com.web.puppylink.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.puppylink.model.FlightTicket;

public interface FlightTicketRepository extends JpaRepository<FlightTicket, String> {
	@Query(value = "SELECT * FROM flightTicket a WHERE DATE(Now()) > DATE(a.arriveDate)",  nativeQuery = true)
	//SELECT * FROM table_a WHERE create_dt >= '2021-03-24 00:00:00' 
	List<FlightTicket> getAfterArrive();
}
