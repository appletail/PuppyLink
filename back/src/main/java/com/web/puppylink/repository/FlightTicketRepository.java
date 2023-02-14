package com.web.puppylink.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.PagingAndSortingRepository;

import com.web.puppylink.model.Board;
import com.web.puppylink.model.FlightTicket;

public interface FlightTicketRepository extends JpaRepository<FlightTicket, String> {
}
